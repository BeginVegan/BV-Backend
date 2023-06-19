package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("menuRepository")
public class MenuRepositoryMariaDB implements MenuRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public int insertMenu(MenuDTO menuInfo) throws AddException {
        log.info("insertMenu 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            return session.insert("com.beginvegan.mybatis.MenuMapper.insertMenu", menuInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("insertMenu 종료");
        }
    }

    @Override
    public void insertAllMenu(List<MenuDTO> menuList) throws AddException {
        log.info("insertAllMenu 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.insert("com.beginvegan.mybatis.MenuMapper.insertAllMenu", menuList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("insertAllMenu 종료");
        }
    }

    @Override
    public MenuDTO selectMenuByMenuNo(int menuNo) throws FindException {
        log.info("selectMenuByMenuNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            MenuDTO menuInfo = session.selectOne("com.beginvegan.mybatis.MenuMapper.selectMenuByMenuNo", menuNo);
            if(menuInfo == null) throw new FindException("해당 메뉴 정보가 없습니다.");
            return menuInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectMenuByMenuNo 종료");
        }
    }

    @Override
    public List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException {
        log.info("selectAllMenuByRestaurantNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<MenuDTO> menuList = session.selectList("com.beginvegan.mybatis.MenuMapper.selectAllMenuByRestaurantNo", restaurantNo);
            if(menuList == null || menuList.isEmpty()) throw new FindException("해당 식당의 메뉴 정보가 없습니다.");
            return menuList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllMenuByRestaurantNo 종료");
        }
    }

    @Override
    public void deleteMenuByMenuNo(int menuNo) throws RemoveException {
        log.info("deleteMenuByMenuNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.delete("com.beginvegan.mybatis.MenuMapper.deleteMenuByMenuNo", menuNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("deleteMenuByMenuNo 종료");
        }
    }

    @Override
    public void deleteAllMenuByRestaurantNo(int RestaurantNo) throws RemoveException {
        log.info("deleteAllMenuByRestaurantNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.delete("com.beginvegan.mybatis.MenuMapper.deleteAllMenuByRestaurantNo", RestaurantNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("deleteAllMenuByRestaurantNo 종료");
        }
    }
}
