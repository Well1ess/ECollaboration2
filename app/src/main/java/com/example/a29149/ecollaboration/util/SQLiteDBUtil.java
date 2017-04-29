package com.example.a29149.ecollaboration.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class SQLiteDBUtil {

    private volatile static SQLiteDBUtil sqLiteDBUtil;
    private SQLiteDatabase sld;
    private Context context;

    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> content;
    private ArrayList<String> time;


    private SQLiteDBUtil() {
    }

    public static SQLiteDBUtil getSqLiteDBUtil() {
        if (sqLiteDBUtil == null) {
            synchronized (SQLiteDBUtil.class) {
                if (sqLiteDBUtil == null) {
                    sqLiteDBUtil = new SQLiteDBUtil();
                }
            }
        }
        return sqLiteDBUtil;
    }

    //数据库端的所有操作
    //创建或打开数据库的方法
    public void createOrOpenDatabase() {
        try {
            sld = SQLiteDatabase.openDatabase
                    (
                            "/data/data/com.example.a29149.ecollaboration/mydb", //数据库所在路径
                            null,                                //CursorFactory
                            SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY //读写、若不存在则创建
                    );
            String sql = "create table if not exists message(id integer primary key autoincrement,name varchar(20),content varchar(20),time varchar(20),team varchar(20),project varchar(20));";
            sld.execSQL(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭数据库的方法
    public void closeDatabase() {
        try {
            sld.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //插入记录的方法
    public void insert(String team, String name, String content, String time, String project) {
        createOrOpenDatabase();
        try {
            ContentValues cvalue = new ContentValues();
            cvalue.put("team", team);
            cvalue.put("project", project);
            cvalue.put("name", name);
            cvalue.put("content", content);
            cvalue.put("time", time);

            sld.insert("message", "id", cvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
        updateList();
    }

    //删除记录的方法
    public void delete(String id) {
        createOrOpenDatabase();
        try {
            String where = "id=" + id;
            sld.delete("message", where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
        updateList();
    }

    //修改记录的方法
    public void update() {
        createOrOpenDatabase();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
    }

    //查询的方法
    public void updateList() {
        id = new ArrayList<>();
        id.clear();
        name = new ArrayList<>();
        name.clear();
        content = new ArrayList<>();
        content.clear();
        time = new ArrayList<>();
        time.clear();

        createOrOpenDatabase();
        try {
            String sql = "select * from message;";
            Cursor cur = sld.rawQuery(sql, new String[]{});
            while (cur.moveToNext()) {
                String strId = cur.getString(0);
                String strName = cur.getString(1);
                String strContent = cur.getString(2);
                String strTime = cur.getString(3);
                id.add(strId);
                name.add(strName);
                content.add(strContent);
                time.add(strTime);
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
    }

    public void updateListByTeamName(String teamNameStr) {
        id = new ArrayList<>();
        id.clear();
        name = new ArrayList<>();
        name.clear();
        content = new ArrayList<>();
        content.clear();
        time = new ArrayList<>();
        time.clear();

        createOrOpenDatabase();
        try {
            String sql = "select * from message where team = '" + teamNameStr + "';";
            Cursor cur = sld.rawQuery(sql, new String[]{});
            while (cur.moveToNext()) {
                String strId = cur.getString(0);
                String strName = cur.getString(1);
                String strContent = cur.getString(2);
                String strTime = cur.getString(3);
                id.add(strId);
                name.add(strName);
                content.add(strContent);
                time.add(strTime);
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
    }

    public int updateListByTeamNameAndProjectName(String teamNameStr, String projectNameStr) {
        id = new ArrayList<>();
        id.clear();
        name = new ArrayList<>();
        name.clear();
        content = new ArrayList<>();
        content.clear();
        time = new ArrayList<>();
        time.clear();

        createOrOpenDatabase();
        try {
            String sql = "select * from message where team = '" + teamNameStr + "' and project = '" + projectNameStr + "';";
            Cursor cur = sld.rawQuery(sql, new String[]{});
            while (cur.moveToNext()) {
                String strId = cur.getString(0);
                String strName = cur.getString(1);
                String strContent = cur.getString(2);
                String strTime = cur.getString(3);
                id.add(strId);
                name.add(strName);
                content.add(strContent);
                time.add(strTime);
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDatabase();
        return time.size();
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }
}
