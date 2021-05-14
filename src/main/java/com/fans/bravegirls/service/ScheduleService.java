package com.fans.bravegirls.service;

import com.fans.bravegirls.vo.model.ScheduleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.ScheduleDao;
import com.fans.bravegirls.common.utils.HTTPUtil;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleDao scheduleDao;


    /**
     * 타임존에 따른 시간 변환
     *
     * @param localTime
     * @param zone
     * @return
     */
    public String timezone_change(String localTime, String zone) {

        String outSLocalTime = localTime;

        try {
            DateFormat in_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

            Date date = in_format.parse(localTime);

            TimeZone tz = TimeZone.getTimeZone(zone);

            DateFormat out_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            out_format.setTimeZone(tz);

            outSLocalTime = out_format.format(date);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return outSLocalTime;
    }


    public void call_scheduled() {

        String zone = "Asia/Seoul";


        try {

            TimeZone tz = TimeZone.getTimeZone(zone);

            Date now_date = new Date();

            DateFormat reg_format = new SimpleDateFormat("yyyyMM");
            String reg_yyyymm = reg_format.format(now_date);

            reg_yyyymm = "202103";

            DateFormat in_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            in_format.setTimeZone(tz);

            Calendar start_cal = Calendar.getInstance();
            start_cal.setTime(now_date);
            start_cal.set(Calendar.DATE, 1);
            start_cal.set(Calendar.HOUR, 0);
            start_cal.set(Calendar.MINUTE, 0);
            start_cal.set(Calendar.SECOND, 0);

            String startLocalTime = in_format.format(start_cal.getTime());
            startLocalTime = "2021-03-01T00:00:00+09:00";
            System.out.println("startLocalTime = " + startLocalTime);

            Calendar end_cal = Calendar.getInstance();
            end_cal.setTime(now_date);
            end_cal.add(Calendar.YEAR, 1);
            end_cal.set(Calendar.DATE, 1);
            end_cal.set(Calendar.HOUR, 0);
            end_cal.set(Calendar.MINUTE, 0);
            end_cal.set(Calendar.SECOND, 0);

            System.out.println("endLocalTime: " + in_format.format(end_cal.getTime()));

            String endLocalTime = in_format.format(end_cal.getTime());

            scheduled(reg_yyyymm, startLocalTime, endLocalTime);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void scheduled(String reg_yyyymm, String paramStartLocalTime, String paramEndLocalTime) {


        String url = "https://cafe.daum.net/_c21_/api/schedule?grpid=1OVS4&fldid=Uvds&";

        //String param1 = URLEncoder.encode("2021-05-01T00:00:00+09:00");
        //String param2 = URLEncoder.encode("2021-12-31T00:00:00+09:00");

        String param1 = URLEncoder.encode(paramStartLocalTime);
        String param2 = URLEncoder.encode(paramEndLocalTime);

        String param = "startTime=" + param1 + "&endTime=" + param2;


        //HTTPUtil httpManager = new HTTPUtil("https://cafe.daum.net/_c21_/api/schedule?grpid=1OVS4&fldid=Uvds&startTime=2021-05-01T00%3A00%3A00%2B09%3A00&endTime=2021-06-01T00%3A00%3A00%2B09%3A00");
        HTTPUtil httpManager = new HTTPUtil(url + param);

        String result = httpManager.httpGetSend(null);

        System.out.println(result);

        try {

            if(result == null || result.length() == 0) {
                System.out.println("호출 실패");
                return;
            }

            //기존 등록된 일정 삭제 한다.
            scheduleDao.deleteScheduled(reg_yyyymm);


            JSONParser jparser = new JSONParser();
            JSONObject json = (JSONObject) jparser.parse(result);

            JSONArray jarray = (JSONArray) json.get("scheduleList");

            int size = jarray.size();

            if(size > 0) {

                ArrayList<ScheduleVo> schedule_array = new ArrayList<>();

                for(int i = 0; i < size; i++) {

                    JSONObject sch_obj = (JSONObject) jarray.get(i);

                    long scheduleId = (Long) sch_obj.get("scheduleId");

                    String title = (String) sch_obj.get("title");


                    String startTime = (String) sch_obj.get("startTime");
                    String endTime = (String) sch_obj.get("endTime");
                    String startLocalTime = (String) sch_obj.get("startLocalTime");
                    String endLocalTime = (String) sch_obj.get("endLocalTime");
                    boolean allDay = (boolean) sch_obj.get("allDay");
                    String allDayStr = "N";
                    String description = (String) sch_obj.get("description");
                    String image = (String) sch_obj.get("image");
                    String location = (String) sch_obj.get("location");


                    if(allDay) {
                        allDayStr = "Y";
                    }

                    //카테고리 정보
                    JSONObject cate_info = (JSONObject) sch_obj.get("category");

                    long categoryId = (Long) cate_info.get("categoryId");
                    String name = (String) cate_info.get("name");
                    String color = (String) cate_info.get("color");

                    //타임존 정보, 없을수도 있다.
                    JSONObject time_info = (JSONObject) sch_obj.get("timezone");

                    String timezone = "Asia/Seoul";    //디폴트 한국

                    if(time_info != null) {
                        timezone = (String) time_info.get("timezone");
                    }

                    String regYyyymm = "";
                    Date reg_date = null;


                    //시간이 있다면 타임존으로 바꾼다.
                    if("N".equals(allDayStr)) {

                        DateFormat in_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                        reg_date = in_format.parse(startLocalTime);


                        startLocalTime = timezone_change(startLocalTime, timezone);
                        endLocalTime = timezone_change(endLocalTime, timezone);
                    } else {
                        DateFormat in_format = new SimpleDateFormat("yyyy-MM-dd");
                        reg_date = in_format.parse(startLocalTime);

                        DateFormat out_format = new SimpleDateFormat("yyyy.MM.dd");

                        startLocalTime = out_format.format(reg_date);

                        endLocalTime = startLocalTime;
                    }

                    DateFormat reg_format = new SimpleDateFormat("yyyyMM");

                    regYyyymm = reg_format.format(reg_date);


                    ScheduleVo scheduleVo = new ScheduleVo();

                    scheduleVo.setScheduleId(String.valueOf(scheduleId));
                    scheduleVo.setTitle(title);
                    scheduleVo.setStartTime(startTime);
                    scheduleVo.setEndTime(endTime);
                    scheduleVo.setStartLocalTime(startLocalTime);
                    scheduleVo.setEndLocalTime(endLocalTime);
                    scheduleVo.setAllDay(allDayStr);
                    scheduleVo.setDescription(description);
                    scheduleVo.setImage(image);
                    scheduleVo.setLocation(location);
                    scheduleVo.setCategoryId(String.valueOf(categoryId));
                    scheduleVo.setName(name);
                    scheduleVo.setColor(color);
                    scheduleVo.setTimezone(timezone);
                    scheduleVo.setRegYyyymm(regYyyymm);

                    schedule_array.add(scheduleVo);
                }


                for(ScheduleVo one_obj : schedule_array) {
                    System.out.println(one_obj);

                    scheduleDao.insertScheduled(one_obj);
                }


            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    //임시조회
    public List<ScheduleVo> selectScheduled(String regYyyymm) {

        if(regYyyymm == null || regYyyymm.length() == 0) {

            Date now_date = new Date();

            DateFormat reg_format = new SimpleDateFormat("yyyyMM");
            regYyyymm = reg_format.format(now_date);

        }

        return scheduleDao.selectScheduled(regYyyymm);
    }
}



