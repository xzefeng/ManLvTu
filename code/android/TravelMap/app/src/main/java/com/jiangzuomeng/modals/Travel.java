package com.jiangzuomeng.modals;

import android.content.ContentValues;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class Travel extends ManLvTuNetworkDataType implements ManLvTuSQLDataType {
    public static final String TRAVEL_TABLE_NAME = "travel";
    public int id;
    public int userId;
    public String name;

    public static Travel fromJson(String json, boolean withId) throws JSONException {
        Travel result = new Travel();
        JSONTokener parser = new JSONTokener(json);
        JSONObject jsonObject = (JSONObject) parser.nextValue();
        if (withId)
            result.id = jsonObject.getInt("id");
        result.userId = jsonObject.getInt("userId");
        result.name = jsonObject.getString("name");
        return result;
    }

    public String toJson(boolean withId) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        if (withId)
            jsonObject.put("id", id);
        jsonObject.put("userId", this.userId);
        jsonObject.put("name", this.name);

        return jsonObject.toString();
    }

    @Override
    public ContentValues makeSQLContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", this.userId);
        contentValues.put("name", this.name);
        return contentValues;
    }

    public static String makeCreateTableSQLString() {
        return "CREATE TABLE IF NOT EXISTS " + TRAVEL_TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, name TEXT)";
    }

    @Override
    public URL getAddUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL)
                .appendPath(StaticStrings.ADD)
                .appendQueryParameter(StaticStrings.USER_ID, Integer.toString(userId))
                .appendQueryParameter(StaticStrings.NAME, name);
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getQueryUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL).appendPath(StaticStrings.QUERY)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id));
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getUpdateUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL).appendPath(StaticStrings.UPDATE)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id))
                .appendQueryParameter(StaticStrings.USER_ID, Integer.toString(userId))
                .appendQueryParameter(StaticStrings.NAME, name);
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getRemoveUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL).appendPath(StaticStrings.REMOVE)
                .appendQueryParameter(StaticStrings.ID, Integer.toString(id));
        URL url = new URL(builder.build().toString());
        return url;
    }

    @Override
    public URL getQueryAllUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(StaticStrings.HTTP)
                .encodedAuthority(StaticStrings.host)
                .appendPath(StaticStrings.TRAVEL).appendPath(StaticStrings.QUERY_ALL)
                .appendQueryParameter(StaticStrings.USER_ID, Integer.toString(userId));
        URL url = new URL(builder.build().toString());
        return url;
    }
}
