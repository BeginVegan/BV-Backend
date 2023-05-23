package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository("MemberRepository")
public class MemberRepositoryMariaDB implements MemberRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @return
     */
    @Override
    public List<MemberDTO> getMemberList() {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<MemberDTO> memberList = session.selectList("com.beginvegan.mybatis.MemberMapper.selectMemberAll");
            return memberList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return null;
    }

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @return
     */
    @Override
    public MemberDTO getMemberByName(String name) {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            MemberDTO member = session.selectOne("com.beginvegan.mybatis.MemberMapper.selectMemberByName", name);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return null;
    }
}
