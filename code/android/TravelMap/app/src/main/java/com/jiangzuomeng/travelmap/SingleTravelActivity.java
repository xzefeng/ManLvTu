package com.jiangzuomeng.travelmap;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.jiangzuomeng.Adapter.SingleTravelItemListViewAdapter;

import java.util.ArrayList;

public class SingleTravelActivity
        extends AppCompatActivity
        implements AMap.OnMapClickListener, AdapterView.OnItemLongClickListener
        ,AdapterView.OnItemClickListener{

    private MapView mapView;
    private AMap aMap;
    private Polyline polyline;
    private ArrayList markersLocation = new ArrayList<>();
    private ArrayList newAddedMarkerLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_travel);

        // setup single travel activity list view
        ListView listView_drawer = (ListView)findViewById(R.id.SingleTravelMapListView);
        listView_drawer.setLongClickable(true);
        listView_drawer.setOnItemLongClickListener(this);
        listView_drawer.setOnItemClickListener(this);
        SingleTravelItemListViewAdapter singleTravelItemAdapter = new SingleTravelItemListViewAdapter(this);
        listView_drawer.setAdapter(singleTravelItemAdapter);

        // set map
        mapView = (MapView) findViewById(R.id.SingleTravelMapMapView);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setupMap();

        // setting action bar
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("在生物岛");
        supportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
    }

    private void setupMap() {
        aMap.setOnMapClickListener(this);
    }

    private void addMarkerToMap(LatLng location) {
        markersLocation.add(location);
        aMap.addMarker(new MarkerOptions().position(location));
        linkMarkersOfMap();
    }

    private void linkMarkersOfMap() {
        if (polyline != null) {
            polyline.remove();
        }
        polyline = aMap.addPolyline(new PolylineOptions().addAll(markersLocation));
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.v("ekuri", latLng.toString());
        addMarkerToMap(latLng);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        View popViewContent = getLayoutInflater().inflate(R.layout.popup_window_for_single_travel_view_list_item, null);

        Button editButton = (Button) popViewContent.findViewById(R.id.SingleTravelActivityListViewPopupEditButton);
        Button deleteButton = (Button) popViewContent.findViewById(R.id.SingleTravelActivityListViewPopupDeleteButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ekuri", "edit button clicked");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ekuri", "delete button clicked");
            }
        });

        PopupWindow popupWindow = new PopupWindow(popViewContent, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        Log.v("ekuri", "view height: " + view.getHeight() + " width: " + view.getWidth());
        popupWindow.showAsDropDown(view, view.getWidth() / 2, -view.getHeight() / 2);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add_new_position:
                LatLng cameraPosition = aMap.getCameraPosition().target;
                newAddedMarkerLocation.add(cameraPosition);
                aMap.addMarker(new MarkerOptions().position(cameraPosition).draggable(true));
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AlbumViewerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_travel_activity_actionbar_menu, menu);
        return true;
    }
}
