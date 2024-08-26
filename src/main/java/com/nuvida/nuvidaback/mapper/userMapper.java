package com.nuvida.nuvidaback.mapper;

import com.nuvida.nuvidaback.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface userMapper {



    public Plans getPlan(String user_id);

    public List<Post> hotPost();

    public Baseball getMatch();

    public Batting getBatting(String bs_seq);

    public Batting userBT(String user_id, String bs_seq);


    void setUserBt(String user_id, int bs_seq, int op_seq, int bt_point);

    int getOpSeq(String selectedTeam);

    void userPoint(String user_id, int bt_point, int user_point);

    Users setuserInfo(String user_id);

    void UpDateUserBt(String user_id, int bs_seq, int nowPoint, int bt_point);


    List<Batting> getWeekMatch();

    void getPoint(String user_id, String bs_seq, int getPoint);

    List<Baseball> getMatchList();


    List<Post> getCmtList();

    Post getCmtInfo(String post_seq);

    List<IMAGES> getImages(String post_seq);

    List<Comments> getComments(String post_seq);

    void insertCmt(String post_seq, String cmt_detail, String user_id, int cmt_num, int cmt_level);

    int getInt(String post_seq, String user_id);

    int insertInt(String post_seq, String user_id);

    int delInt(String post_seq, String user_id);

    int insertPost(Post postInfo);

    void insertImg(int post_seq, String img);

    List<Batting> getBattingList(String user_id);

    List<Batting> getUserBattingList(String user_id);

    int getUserPoint(String user_id);

    List<Users> getFriend(String user_id);

    void insertPlan(Plans plan);

    void insertMember(int plan_seq, String user_id, String mem_type);

    void insertTravletime(int plan_seq, String travel_date, String startTime, String endTime);

    void insertRoute(int plan_seq,int seq,String title,String addr,String image,String contentid,String contenttypeid,float lat,float lng,String reser_dt,String travel_date);

    void insertAcc(int plan_seq, String acc_name,String acc_addr, String check_in, String check_out, float lat,float lng,String contentid);

    Users signin(String user_id, String user_pw);

    int checkId(String user_id);

    void signUp(String user_id, String user_pw, String name, String phone);

    List<Plans> getPlanList(String user_id);

    void updatePw(String user_id, String user_pw);

    void updateUserInfo(String user_id, String user_nick, String profile_img, String user_phone);

    Users getUserInfo(String user_id);
}
