package pl.gtug.szczecin.hackathon.database.generated;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TODO_ITEM.
*/
public class TodoItemDao extends AbstractDao<TodoItem, Long> {

    public static final String TABLENAME = "TODO_ITEM";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Description = new Property(1, String.class, "description", false, "DESCRIPTION");
        public final static Property Is_done = new Property(2, boolean.class, "is_done", false, "IS_DONE");
        public final static Property LocationId = new Property(3, Long.class, "locationId", false, "LOCATION_ID");
    };

    private Query<TodoItem> location_TodoItemListQuery;

    public TodoItemDao(DaoConfig config) {
        super(config);
    }
    
    public TodoItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String sql = "CREATE TABLE " + (ifNotExists? "IF NOT EXISTS ": "") + "'TODO_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DESCRIPTION' TEXT NOT NULL ," + // 1: description
                "'IS_DONE' INTEGER NOT NULL ," + // 2: is_done
                "'LOCATION_ID' INTEGER);"; // 3: locationId
        db.execSQL(sql);
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TODO_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TodoItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDescription());
        stmt.bindLong(3, entity.getIs_done() ? 1l: 0l);
 
        Long locationId = entity.getLocationId();
        if (locationId != null) {
            stmt.bindLong(4, locationId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TodoItem readEntity(Cursor cursor, int offset) {
        TodoItem entity = new TodoItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // description
            cursor.getShort(offset + 2) != 0, // is_done
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // locationId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TodoItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDescription(cursor.getString(offset + 1));
        entity.setIs_done(cursor.getShort(offset + 2) != 0);
        entity.setLocationId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected Long updateKeyAfterInsert(TodoItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TodoItem entity) {
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
    
    /** Internal query to resolve the "todoItemList" to-many relationship of Location. */
    public synchronized List<TodoItem> _queryLocation_TodoItemList(Long locationId) {
        if (location_TodoItemListQuery == null) {
            QueryBuilder<TodoItem> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.LocationId.eq(locationId));
            location_TodoItemListQuery = queryBuilder.build();
        } else {
            location_TodoItemListQuery.setParameter(0, locationId);
        }
        return location_TodoItemListQuery.list();
    }

}
