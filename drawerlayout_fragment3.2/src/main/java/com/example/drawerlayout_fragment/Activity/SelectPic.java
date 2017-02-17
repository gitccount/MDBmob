package com.example.drawerlayout_fragment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.drawerlayout_fragment.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.drawerlayout_fragment.Activity.gridview.toShow;

public class SelectPic extends AppCompatActivity {
    private static final int SELECT_IMAGE_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(SelectPic.this, PhotoSelectorActivity.class);
                intent.putExtra(PhotoSelectorActivity.KEY_MAX, 6);//选择多少张图片，默认最大值为10
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, SELECT_IMAGE_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == SELECT_IMAGE_CODE) {// selected image
            if (data != null && data.getExtras() != null) {
                @SuppressWarnings("unchecked")
                List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
                ArrayList<String> infoList = new ArrayList<String>();
                if (photos == null || photos.isEmpty()) {
                    Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(this, YourOwnLogic.class);
//                    Bundle b = new Bundle();
//                    b.putSerializable("album_pojo", albumPojo);
//                    b.putSerializable("photos", (Serializable) photos);
//                    intent.putExtras(b);
//                    startActivity(intent);
//                    finish();
                    for (PhotoModel pm : photos) {
                        infoList.add(pm.getOriginalPath());
                    }
                    gridview.toShow(this,infoList);

                }
            }
        }
    }

}
