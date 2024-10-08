package com.nuvida.nuvidaback.controller;

import com.nuvida.nuvidaback.entity.*;
import com.nuvida.nuvidaback.mapper.userMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Notification;
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
    public int insertPlan(@RequestBody Map<String, Object> requestData){
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

        String msg = plan_name+" 일정에 초대되었습니다.";

//        여행 멤버
        if(members != null && members.size() > 0){

            for(String member : members){
                mem_type = "1";
                mapper.insertMember(plan_seq,member,mem_type);
                mapper.memNoti(member, msg);
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

        return plan_seq;

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

    // 여행 목록
    @RequestMapping("/getPlanList")
    public List<Plans> getPlanList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        List<Plans> plansList = mapper.getPlanList(user_id);

        return plansList;
    }

    // 회원정보수정
    @RequestMapping("/updateUserInfo")
    public Users updateUserInfo(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String user_pw = requestData.get("user_pw");
        String profile_img = requestData.get("profile_img");
        String user_nick= requestData.get("user_nick");
        String user_phone = requestData.get("user_phone");

        if(user_pw != null){
            mapper.updatePw(user_id, user_pw);
        }

        mapper.updateUserInfo(user_id, user_nick, profile_img, user_phone);

        Users userInfo = mapper.getUserInfo(user_id);

        return userInfo;

    }


    // 관심 목록
    @RequestMapping("/getFavoriteList")
    public List<Post> getFavoriteList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        List<Post> favoritelist = mapper.getFavoriteList(user_id);

        return favoritelist;
    }


    // 작성한 글 목록
    @RequestMapping("/getCommunityList")
    public List<Post> getCommunityList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        List<Post> communityList = mapper.getCommunityList(user_id);

        return communityList;
    }

    // 작성한 댓글 목록
    @RequestMapping("/getCommentList")
    public List<Comments> getCommentList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        List<Comments> commentList = mapper.getCommentList(user_id);

        return commentList;
    }

    // 글 삭제
    @RequestMapping("/deletePost")
    public void deletePost(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String post_seq = requestData.get("post_seq");

        mapper.delPost(post_seq, user_id);

    }


    // 댓글 삭제
    @RequestMapping("/deleteComment")
    public void deleteComment(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String cmt_seq = requestData.get("post_seq");

        mapper.delComment(cmt_seq, user_id);

    }

    // 요청 목록 가져오기
    @RequestMapping("/getRequestList")
    public List<Users> getRequestList(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        List<Users> frList = mapper.getRequestList(user_id);
        return frList;
    }

    // 친구 요청
    @RequestMapping("/requestFriend")
    public int requestFriend(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String request_id = requestData.get("request_id");

        int check = mapper.checkId(request_id);

        if(check == 0){
            return check;
        }

        int fr_check = mapper.checkFr(user_id,request_id);

        if(fr_check > 0){
            return 1;
        }

        mapper.requestFriend(user_id, request_id);
        mapper.requestedFriend(user_id, request_id);
        mapper.notiRequest(request_id);

        return 2;
    }

    // 친구 삭제
    @RequestMapping("/delFriend")
    public void delFriend(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String fr_user = requestData.get("fr_user");

        mapper.delFriend(user_id, fr_user);
        mapper.delFriend(fr_user, user_id);

    }

    // 친구 수락
    @RequestMapping("/acceptFriend")
    public void acceptFriend(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String fr_user = requestData.get("fr_user");

        mapper.accept(user_id, fr_user);
        mapper.accept(fr_user, user_id);

        String msg = user_id+"님께서 친구 요청을 수락하셨습니다.";

        mapper.resultNori(fr_user, msg);

    }

    // 친구 거절
    @RequestMapping("/refusalFriend")
    public void refusalFriend(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        String fr_user = requestData.get("fr_user");

        mapper.refusal(user_id, fr_user);
        mapper.refusal(fr_user, user_id);

        String msg = user_id+"님께서 친구 요청을 거절하셨습니다.";

        mapper.resultNori(fr_user, msg);

    }

    // 알림 체크
    @RequestMapping("/checkNoti")
    public int checkNoti(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");

        int check = mapper.checkNoti(user_id);

        return check;
    }

    // 알림 페이지 이동
    @RequestMapping("/setNoti")
    public List<Notice> setNoti(@RequestBody Map<String, String> requestData){
        String user_id = requestData.get("user_id");
        mapper.readNotice(user_id);
        List<Notice> notiList = mapper.getNoticeList(user_id);

        return notiList;
    }

    // 루트 목록 가져오기
    @RequestMapping("/getRouteList")
    public List<Route> getRouteList(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        List<Route> routeList = mapper.getRouteList(plan_seq);

        return routeList;
    }
    
    // 호텔 목록 가져오기
    @RequestMapping("/getAcc")
    public List<ACCOMMODATIONS> getAcc(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        List<ACCOMMODATIONS> accList = mapper.getAcc(plan_seq);

        return accList;
    }

    // 여행정보 가져오기
    @RequestMapping("/getPlanInfo")
    public Plans getPlanInfo(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        Plans planInfo = mapper.getPlanInfo(plan_seq);

        return planInfo;
    }

    //예약 목록 가져오기
    @RequestMapping("/getReser")
    public List<Route> getReser(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        List<Route> routeList = mapper.getReser(plan_seq);

        return routeList;
    }

    //교통편 목록 가져오기
    @RequestMapping("/getTrans")
    public List<TRANSPORTATIONS> getTrans(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        List<TRANSPORTATIONS> transList = mapper.getTrans(plan_seq);

        return transList;
    }

    //교통편 등록
    @RequestMapping("/setTrans")
    public void setTrans(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        String tr_name = requestData.get("tr_name");
        String tr_dt = requestData.get("tr_dt");

        mapper.setTrans(plan_seq, tr_name, tr_dt);
    }

    //교통편 등록
    @RequestMapping("/setReser")
    public void setReser(@RequestBody Map<String, String> requestData){
        String route_seq = requestData.get("route_seq");
        String reser_dt = requestData.get("reser_dt");

        mapper.setReser(route_seq, reser_dt);
    }
    
    //숙소 등록
    @RequestMapping("/setAcc")
    public void setAcc(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        String acc_name = requestData.get("acc_name");
        String acc_addr = requestData.get("acc_addr");
        String check_in = requestData.get("check_in");
        String check_out = requestData.get("check_out");
        float lat = Float.parseFloat(requestData.get("lat"));
        float lng = Float.parseFloat(requestData.get("lng"));
        String contentid = requestData.get("contentid");
        String contenttypeid = requestData.get("contenttypeid");

        mapper.setAcc(plan_seq, acc_name, acc_addr, check_in, check_out, lat, lng, contentid, contenttypeid);
    }

    //교통편 제거
    @RequestMapping("/delTrans")
    public void delTrans(@RequestBody Map<String, String> requestData){
        String tr_seq = requestData.get("tr_seq");

        mapper.delTrans(tr_seq);
    }

    //예약 제거
    @RequestMapping("/delReser")
    public void delReser(@RequestBody Map<String, String> requestData){
        String route_seq = requestData.get("route_seq");

        mapper.delReser(route_seq);
    }

    //숙소 제거
    @RequestMapping("/delAcc")
    public void delAcc(@RequestBody Map<String, String> requestData){
        String acc_seq = requestData.get("acc_seq");

        mapper.delAcc(acc_seq);
    }

    // 멤버 목록 가져오기
    @RequestMapping("/getMember")
    public List<MEMBERS> getMember(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        List<MEMBERS> memberList = mapper.getMember(plan_seq);

        return memberList;
    }


    // 멤버 목록 가져오기
    @RequestMapping("/getLeader")
    public Boolean getLeader(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        String user_id = requestData.get("user_id");

        String type = mapper.getLeader(plan_seq,user_id);

        return type.equals("0");
    }

    // 멤버 목록 가져오기
    @RequestMapping("/setMember")
    public void setMember(@RequestBody Map<String, String> requestData){
        String plan_name = requestData.get("plan_name");
        String user_id = requestData.get("user_id");
        int plan_seq = Integer.parseInt(requestData.get("plan_seq"));

        String msg = plan_name+" 일정에 초대되었습니다.";

        String mem_type = "1";
        mapper.insertMember(plan_seq, user_id,mem_type);
        mapper.memNoti(user_id, msg);
    }

    // 멤버 제거
    @RequestMapping("/deleteMember")
    public void deleteMember(@RequestBody Map<String, String> requestData){
        String mem_seq = requestData.get("mem_seq");
        mapper.deleteMember(mem_seq);
    }

    // 멤버 제거
    @RequestMapping("/getMemCount")
    public int getMemCount(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        int memCount = mapper.getMemCount(plan_seq);

        return memCount;
    }

    //정산 목록 가져오기
    @RequestMapping("/getCalculate")
    public List<CALCULATE> getCalculate(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        List<CALCULATE> calList = mapper.getCalculate(plan_seq);

        return calList;
    }

    // 정산 추가 하기
    @RequestMapping("/addCalculate")
    public void addCalculate(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        String title = requestData.get("title");
        int price = Integer.parseInt(requestData.get("price"));

        mapper.addCalculate(plan_seq,title,price);
    }

    // 정산 제거
    @RequestMapping("/delCalculate")
    public void delCalculate(@RequestBody Map<String, String> requestData){
        String cal_seq = requestData.get("cal_seq");

        mapper.delCalculate(cal_seq);
    }

    // 멤버 일정 제거
    @RequestMapping("/delPlanMem")
    public void delPlanMem(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");
        String user_id = requestData.get("user_id");

        mapper.delPlanMem(plan_seq, user_id);
    }

    // 리더 일정 제거
    @RequestMapping("/delPlanLeader")
    public void delPlanLeader(@RequestBody Map<String, String> requestData){
        String plan_seq = requestData.get("plan_seq");

        mapper.delPlanLeader(plan_seq);
    }
    
    
}
