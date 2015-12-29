package com.dev.tim.shopper_data.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.dev.tim.shopper_data.greendao.ItemDb;

import com.dev.tim.shopper_data.greendao.ItemDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig itemDbDaoConfig;

    private final ItemDbDao itemDbDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        itemDbDaoConfig = daoConfigMap.get(ItemDbDao.class).clone();
        itemDbDaoConfig.initIdentityScope(type);

        itemDbDao = new ItemDbDao(itemDbDaoConfig, this);

        registerDao(ItemDb.class, itemDbDao);
    }
    
    public void clear() {
        itemDbDaoConfig.getIdentityScope().clear();
    }

    public ItemDbDao getItemDbDao() {
        return itemDbDao;
    }

}
