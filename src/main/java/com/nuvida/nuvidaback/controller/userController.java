package com.nuvida.nuvidaback.controller;

import com.nuvida.nuvidaback.entity.*;
import com.nuvida.nuvidaback.mapper.userMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
public class userController {

    @Autowired
    private userMapper mapper;


    // 여행 일정
    @RequestMapping("/getPlan")
    public Plans getPlan(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        Plans planInfo = mapper.getPlan(user_id);
        return planInfo;
    }

    //    핫게 목록
    @RequestMapping("/hotPost")
    public List<Post> hotPost(){
        List<Post> hotPostList = mapper.hotPost();
        return hotPostList;
    }

    //    경기 정보
    @RequestMapping("/getMatch")
    public Baseball getMatch(){
        Baseball matchInfo = mapper.getMatch();
        return matchInfo;
    }

    //    경기 배팅 정보
    @RequestMapping("/getBtPoin")
    public Batting getBtPoin(@RequestBody Map<String, String> requestData){
        String bs_seq = requestData.get("bs_seq");
        System.out.println("bt"+bs_seq);
        Batting matchBt = mapper.getBatting(bs_seq);
        return matchBt;
    }

    //    유저 배팅 정보 가져오기
    @RequestMapping("/getUserBtPoin")
    public Batting getUserBtPoin(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String bs_seq = requestData.get("bs_seq");
        System.out.println(bs_seq);

        Batting userBtInfo = mapper.userBT(user_id, bs_seq);


        System.out.println("실행");

        return userBtInfo;
    }

    //    배팅하기
    @RequestMapping("/setUserBT")
    public void setUserBT(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        int bs_seq = Integer.parseInt(requestData.get("bs_seq"));
        String selectedTeam = requestData.get("selectedTeam");
        int bt_point = Integer.parseInt(requestData.get("betPoint"));
        int user_point = mapper.getUserPoint(user_id);

        System.out.println(user_id);
        System.out.println(bs_seq);
        System.out.println(selectedTeam);
        System.out.println(bt_point);

        int op_seq = mapper.getOpSeq(selectedTeam);

        mapper.setUserBt(user_id,bs_seq,op_seq,bt_point);
        mapper.userPoint(user_id, bt_point, user_point);

    }

    //    유저 배팅 정보 업데이트
    @RequestMapping("/UpDateUserBT")
    public void UpDateUserBT(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        int bs_seq = Integer.parseInt(requestData.get("bs_seq"));
        int nowPoint = Integer.parseInt(requestData.get("nowPoint"));
        int bt_point = Integer.parseInt(requestData.get("betPoint"));
        int user_point = mapper.getUserPoint(user_id);

        System.out.println(user_id);
        System.out.println(bs_seq);
        System.out.println(nowPoint);
        System.out.println(bt_point);


        mapper.UpDateUserBt(user_id,bs_seq,nowPoint,bt_point);
        mapper.userPoint(user_id, bt_point, user_point);

    }

    //    유저 정보 다시 가져오기
    @RequestMapping("/setUser")
    public Users setUser(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        Users userInfo = mapper.setuserInfo(user_id);
        return userInfo;

    }

    //    주간 경기 목록
    @RequestMapping("/getWeeklyMatchData")
    public List<Batting> getWeeklyMatchData(){
        List<Batting> weekMatchList = mapper.getWeekMatch();
        return weekMatchList;

    }

    //    경기 종료 후 포인트 회수
    @RequestMapping("/getPoin")
    public Batting getPoin(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String bs_seq = requestData.get("bs_seq");
        int get_point = Integer.parseInt(requestData.get("getPoint"));
        int user_point = mapper.getUserPoint(user_id);

        int getPoint = get_point + user_point;

        System.out.println(bs_seq);

        mapper.getPoint(user_id, bs_seq, getPoint);

        Batting userBtInfo = mapper.userBT(user_id, bs_seq);


        System.out.println("실행");

        return userBtInfo;
    }

    // 경기 목록
    @RequestMapping("/matcheList")
    public List<Baseball> matcheList(){
        List<Baseball> matcheList = mapper.getMatchList();

        return matcheList;
    }



    // 커뮤니티 목록
    @RequestMapping("/getCmtList")
    public List<Post> getCmtList(){
        List<Post> cmtList = mapper.getCmtList();

        return cmtList;
    }

    // 글 상세 보기
    @RequestMapping("/getCmtInfo")
    public Post getCmtInfo(@RequestBody Map<String, String> requestData){
        String post_seq = requestData.get("post_seq");
        Post cmtDetail = mapper.getCmtInfo(post_seq);
        List<IMAGES> images = mapper.getImages(post_seq);
        List<Comments> comments = mapper.getComments(post_seq);

        Post cmtInfo = new Post(cmtDetail.getPost_seq(),cmtDetail.getUser_nick(),cmtDetail.getProfile_img(),cmtDetail.getPost_title(),cmtDetail.getDetails(),cmtDetail.getRegi_at(),images,cmtDetail.getIntCount(),comments);

        return cmtInfo;
    }

    //    관심등록 여부
    @RequestMapping("/getInt")
    public Boolean getInt(@RequestBody Map<String, String> requestData){
        String post_seq = requestData.get("post_seq");
        String user_id = requestData.get("user_id");

        int cnt = mapper.getInt(post_seq, user_id);

        if (cnt > 0){
            return true;
        }else {
            return false;
        }
    }

    //    댓글 작성
    @RequestMapping("/insertCmt")
    public Post insertCmt(@RequestBody Map<String, String> requestData){
        String post_seq = requestData.get("post_seq");
        String cmt_detail = requestData.get("cmt_detail");
        String user_id = requestData.get("user_id");
        int cmt_num = Integer.parseInt(requestData.get("cmt_num"));

        int cmt_level = 0;

        if(cmt_num >0 ){
            cmt_level = 1;
        }

        mapper.insertCmt(post_seq,cmt_detail,user_id,cmt_num,cmt_level);



        Post cmtDetail = mapper.getCmtInfo(post_seq);
        List<IMAGES> images = mapper.getImages(post_seq);
        List<Comments> comments = mapper.getComments(post_seq);

        Post cmtInfo = new Post(cmtDetail.getPost_seq(),cmtDetail.getUser_nick(),cmtDetail.getProfile_img(),cmtDetail.getPost_title(),cmtDetail.getDetails(),cmtDetail.getRegi_at(),images,cmtDetail.getIntCount(),comments);

        return cmtInfo;
    }

    //   관심등록
    @RequestMapping("/insertInt")
    public void insertInt(@RequestBody Map<String, String> requestData){
        String post_seq = requestData.get("post_seq");
        String user_id = requestData.get("user_id");

        mapper.insertInt(post_seq, user_id);


    }

    //   관심등록 해제
    @RequestMapping("/delInt")
    public void delInt(@RequestBody Map<String, String> requestData){
        String post_seq = requestData.get("post_seq");
        String user_id = requestData.get("user_id");

        mapper.delInt(post_seq, user_id);

    }


    //   게시물 작성
    @RequestMapping("/insertPost")
    public void insertPost(@RequestBody Post requestData){
        System.out.println("들어왔음");
        System.out.println(requestData.toString());

        String user_id = requestData.getUser_id();
        String post_title = requestData.getPost_title();
        String details = requestData.getDetails();
        List<String> imageList = requestData.getImageList();

        Post postInfo = new Post(user_id,post_title,details);

        int post =  mapper.insertPost(postInfo);
        System.out.println(postInfo.toString());

        if(imageList != null && imageList.size() > 0){
            System.out.println("이미지 있음");

            int post_seq = postInfo.getPost_seq();

            if(post_seq > 0){
                for(String img : imageList){
                    mapper.insertImg(post_seq, img);
                }
            }

        }else {
            System.out.println("이미지 없음");
        }


    }

    //   베팅목록
    @RequestMapping("/getBattingList")
    public List<Batting> getBattingList(@RequestBody Map<String, String> requestDataa){
        String user_id = requestDataa.get("user_id");

        List<Batting> battingList = mapper.getBattingList(user_id);

        return  battingList;
    }

    //   베팅목록
    @RequestMapping("/getUserBattingList")
    public List<Batting> getUserBattingList(@RequestBody Map<String, String> requestDataa){
        String user_id = requestDataa.get("user_id");

        List<Batting> battingList = mapper.getUserBattingList(user_id);

        return  battingList;
    }

    //    포인트 회수
    @RequestMapping("/getBtPoint")
    public void getBtPoint(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String bs_seq = requestData.get("bs_seq");
        int get_point = Integer.parseInt(requestData.get("getPoint"));
        int user_point = mapper.getUserPoint(user_id);

        int getPoint = get_point + user_point;

        System.out.println(bs_seq);

        mapper.getPoint(user_id, bs_seq, getPoint);

    }

    // 일정 등록
    @RequestMapping("/insertPlan")
    public void insertPlan(@RequestBody Map<String, Object> requestData){
        String user_id = (String) requestData.get("user_id");
        String plan_name = (String) requestData.get("plan_name");
        String start_date = (String) requestData.get("start_date");
        String end_date = (String) requestData.get("end_date");
        Map<String,Object> route = (Map<String, Object>) requestData.get("route");
        List<String> members = (List<String>) requestData.get("members");
        Map<String,Object> traveltime = (Map<String, Object>) requestData.get("traveltime");

        List<String> start_time = (List<String>) traveltime.get("start_time");
        List<String> end_time = (List<String>) traveltime.get("end_time");


        List<Object> accommodation = (List<Object>) requestData.get("accommodation");

        String startDateStr = (String) requestData.get("start_date");
        String endDateStr = (String) requestData.get("end_date");

        // 날짜 형식 지정 (예시: "yyyy-MM-dd'T'HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // 문자열을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        // 두 날짜 사이의 일수 계산
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
        // 결과 출력 또는 다른 작업 수행
        System.out.println("총 일수: " + daysBetween + "일");


        Plans plan = new Plans(plan_name, start_date,end_date);

        // plan 등록
        mapper.insertPlan(plan);

        int plan_seq = plan.getPlan_seq();

        String mem_type = "0";

//        본인 멤버 등록
        mapper.insertMember(plan_seq,user_id,mem_type);


        // 여행 시간
        if(traveltime != null && traveltime.size() > 0){
            for(int i = 0; i < daysBetween; i++){
                String travel_date = startDate.plusDays(i)+"";
                String startTime = start_time.get(i).split("T")[1];
                String endTime = end_time.get(i).split("T")[1];

                mapper.insertTravletime(plan_seq, travel_date, startTime, endTime);
            }
        };


//        이동 경로
        if(route != null && route.size() > 0){
            for(int i = 0; i < daysBetween; i++){
                int seq = 1;
                // 여행 날짜
                String travel_date = startDate.plusDays(i)+"";

                // i일치
                String day = i+1+"일차";

                // 일차별 이동 루트
                List<Object> routeList = (List<Object>) route.get(day);


                for(Object routeObj : routeList){
                    Map<String,String > routeinfo = (Map<String,String>) routeObj;

                    String title = routeinfo.get("name");
                    String addr = routeinfo.get("addr");
                    String image = routeinfo.get("firstimage");
                    String contentid = routeinfo.get("contentid");
                    String contenttypeid = routeinfo.get("contenttypeid");

                    Object latObj = routeinfo.get("lat");
                    Object lngObj = routeinfo.get("lng");

                    float lat = 0;
                    float lng = 0;

                    if (latObj instanceof String) {
                        lat = Float.parseFloat((String) latObj);
                    } else if (latObj instanceof Double) {
                        lat = ((Double) latObj).floatValue();
                    }

                    if (lngObj instanceof String) {
                        lng = Float.parseFloat((String) lngObj);
                    } else if (lngObj instanceof Double) {
                        lng = ((Double) lngObj).floatValue();
                    }


                    String reser_dt = routeinfo.get("reservation");

                    mapper.insertRoute(plan_seq,seq,title,addr,image,contentid,contenttypeid,lat,lng,reser_dt,travel_date);

                    seq++;

                }
            }

        };

//        여행 멤버
        if(members != null && members.size() > 0){

            for(String member : members){
                mem_type = "1";
                mapper.insertMember(plan_seq,member,mem_type);
            }

        };



//        숙소를 방문
        if (accommodation != null && accommodation.size() > 0){
            for(Object accommodationObj : accommodation){
                Map<String,String > accommodationinfo = (Map<String,String>) accommodationObj;
                System.out.println(accommodationinfo.get("acc_name"));
                String acc_name = accommodationinfo.get("acc_name");
                String acc_addr = accommodationinfo.get("addr");
                String check_in = accommodationinfo.get("check_in");
                String check_out = accommodationinfo.get("check_out");
                float lat = Float.parseFloat(accommodationinfo.get("lat"));
                float lng = Float.parseFloat(accommodationinfo.get("lng"));
                String contentid = accommodationinfo.get("contentid");

                mapper.insertAcc(plan_seq,acc_name,acc_addr,check_in, check_out, lat,lng,contentid);
            }
        };

    };

    //    친구 목록 가져오기
    @RequestMapping("/getFriend")
    public List<Users> getFriend(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        List<Users> frList = mapper.getFriend(user_id);
        return frList;
    }


    // 로그인
    @RequestMapping("/signin")
    public Users signin(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("id");
        String user_pw = requestData.get("password");
        Users userInfo = mapper.signin(user_id, user_pw);
        return userInfo;
    }
    
    // 아이디 중복 확인
    @RequestMapping("/idCheck")
    public int idCheck(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("id");
        int cnt = mapper.checkId(user_id);
        return cnt;
    }

    // 아이디 중복 확인
    @RequestMapping("/signUp")
    public void signUp(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("id");
        String user_pw = requestData.get("pw");
        String name = requestData.get("name");
        String phone = requestData.get("phone");

        mapper.signUp(user_id, user_pw, name, phone);
    }

    // 아이디 중복 확인
    @RequestMapping("/getPlanList")
    public List<Plans> getPlanList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        List<Plans> plansList = mapper.getPlanList(user_id);

        return plansList;
    }
}
