package com.dev.tim.shopper_data.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dev.tim.shopper_data.greendao.ItemDb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ITEM_DB.
*/
public class ItemDbDao extends AbstractDao<ItemDb, Long> {

    public static final String TABLENAME = "ITEM_DB";

    /**
     * Properties of entity ItemDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Category = new Property(2, String.class, "category", false, "CATEGORY");
        public final static Property Created_at = new Property(3, String.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(4, String.class, "updated_at", false, "UPDATED_AT");
        public final static Property User_id = new Property(5, String.class, "user_id", false, "USER_ID");
    };


    public ItemDbDao(DaoConfig config) {
        super(config);
    }
    
    public ItemDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ITEM_DB' (" + //
                "'ID' INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'CATEGORY' TEXT," + // 2: category
                "'CREATED_AT' TEXT," + // 3: created_at
                "'UPDATED_AT' TEXT," + // 4: updated_at
                "'USER_ID' TEXT);"); // 5: user_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ITEM_DB'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ItemDb entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(3, category);
        }
 
        String created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindString(4, created_at);
        }
 
        String updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindString(5, updated_at);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(6, user_id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ItemDb readEntity(Cursor cursor, int offset) {
        ItemDb entity = new ItemDb( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // category
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // created_at
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // updated_at
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // user_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ItemDb entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategory(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreated_at(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUpdated_at(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_id(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ItemDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ItemDb entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}