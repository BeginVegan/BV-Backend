package com.beginvegan.repository;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("MemberRepository")
public class MemberRepositoryMariaDB implements MemberRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void insertMember(MemberDTO memberInfo) throws AddException {
        log.info("insertMember 시작 - memberInfo : " + memberInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.MemberMapper.insertMember", memberInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("insertMember 종료");
        }
    }

    @Override
    public void updateMember(MemberDTO memberInfo) throws ModifyException {
        log.info("updateMember 시작 - memberInfo : " + memberInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.update("com.beginvegan.mybatis.MemberMapper.updateMember", memberInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModifyException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("updateMember 종료");
        }
    }

    @Override
    public void deleteMember(String memberEmail) throws RemoveException {
        log.info("deleteMember 시작 - memberEmail : " + memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.delete("com.beginvegan.mybatis.MemberMapper.deleteMember", memberEmail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("deleteMember 종료");
        }
    }

    @Override
    public MemberDTO selectMemberByMemberEmail(String memberEmail) throws FindException {
        log.info("selectMemberByMemberEmail 시작 - memberEmail : " + memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            MemberDTO memberInfo = sqlSession.selectOne("com.beginvegan.mybatis.MemberMapper.selectMemberByMemberEmail", memberEmail);
            if(memberInfo == null) throw new FindException("해당 Email과 일치하는 멤버 정보가 없습니다.");
            return memberInfo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectMemberByMemberEmail 종료");
        }
    }

    @Override
    public void insertPoint(PointDTO pointInfo) throws AddException {
        log.info("insertPoint 시작 - pointInfo : " + pointInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.MemberMapper.insertPoint", pointInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("insertPoint 종료");
        }
    }

    @Override
    public List<PointDTO> selectAllPointsByMemberEmail(String memberEmail) throws FindException {
        log.info("selectAllPointsByMemberEmail 시작 - memberEmail : " + memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<PointDTO> pointList = sqlSession.selectList("com.beginvegan.mybatis.MemberMapper.selectAllPointsByMemberEmail", memberEmail);
            return pointList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectAllPointsByMemberEmail 종료");
        }
    }

    @Override
    public void insertBookmark(int restaurantNo, String memberEmail) throws AddException {
        log.info("insertBookmark 시작 - restaurantNo : " + restaurantNo + ", memberEmail : " + memberEmail);

        Map<String, Object> map = new HashMap<>();
        map.put("restaurantNo", restaurantNo);
        map.put("memberEmail", memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.MemberMapper.insertBookmark", map);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("insertBookmark 종료");
        }
    }

    @Override
    public void deleteBookmark(int restaurantNo, String memberEmail) throws RemoveException {
        log.info("deleteBookmark 시작 - restaurantNo : " + restaurantNo + ", memberEmail : " + memberEmail);

        Map<String, Object> map = new HashMap<>();
        map.put("restaurantNo", restaurantNo);
        map.put("memberEmail", memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.MemberMapper.deleteBookmark", map);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("deleteBookmark 종료");
        }
    }

    @Override
    public List<BookmarkDTO> selectAllBookmarkByMemberEmail(String memberEmail) throws FindException {
        log.info("selectAllBookmarkByMemberEmail 시작 - memberEmail : " + memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<BookmarkDTO> bookmarkList = sqlSession.selectList("com.beginvegan.mybatis.MemberMapper.selectAllBookmarkByMemberEmail", memberEmail);
            return bookmarkList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectAllBookmarkByMemberEmail 종료");
        }
    }

    //아래 메소드는 단위 테스트를 위한 CRUD 메소드입니다.
    // Selects a specific point for testing
    public PointDTO selectPointTEST(PointDTO pointDTO) throws FindException {
        log.info("selectPointTEST 시작 - memberEmail : " + pointDTO.getMemberEmail() + ", getPointTime : " + pointDTO.getPointTime());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            PointDTO point = sqlSession.selectOne("com.beginvegan.mybatis.MemberMapper.selectPointTEST", pointDTO);
            return point;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectPointTEST 종료");
        }
    }

    // Deletes a specific point for testing
    public void deletePointTEST(String memberEmail, Date date) throws RemoveException {
        log.info("deletePointTEST 시작 - memberEmail : " + memberEmail + ", date : " + date);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            Map<String, Object> map = new HashMap<>();
            map.put("memberEmail", memberEmail);
            map.put("date", date);
            sqlSession.delete("com.beginvegan.mybatis.MemberMapper.deletePointTEST", map);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("deletePointTEST 종료");
        }
    }

    // Selects a specific bookmark for testing
    public BookmarkDTO selectBookmarkTEST(int restaurantNo, String memberEmail) throws FindException {
        log.info("selectBookmarkTEST 시작 - restaurantNo : " + restaurantNo + ", memberEmail : " + memberEmail);

        Map<String, Object> map = new HashMap<>();
        map.put("restaurantNo", restaurantNo);
        map.put("memberEmail", memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            BookmarkDTO bookmark = sqlSession.selectOne("com.beginvegan.mybatis.MemberMapper.selectBookmarkTEST", map);
            return bookmark;

        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectBookmarkTEST 종료");
        }
    }
}
