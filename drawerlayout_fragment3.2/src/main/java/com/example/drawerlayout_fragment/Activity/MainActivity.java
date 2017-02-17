package com.example.drawerlayout_fragment.Activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Adapter.HomeListViewAdapter;
import com.example.drawerlayout_fragment.Custom.ImageHelper;
import com.example.drawerlayout_fragment.Custom.ImageViewPlus;
import com.example.drawerlayout_fragment.Custom.RoundCornerImageView;
import com.example.drawerlayout_fragment.Fragment.GameFragment3;
import com.example.drawerlayout_fragment.Fragment.NewsFragment;
import com.example.drawerlayout_fragment.Fragment.TechnologyFragment;
import com.example.drawerlayout_fragment.JavaBean.CopyOfJkUser;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.CommonSharedPreferences;
import com.example.drawerlayout_fragment.config.Conf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.drawerlayout_fragment.Activity.up.LongToast;
/*
/听安家了头文件
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int IMAGE_REQUEST_CODE = 0, CAMERA_REQUEST_CODE = 1,
            RESIZE_REQUEST_CODE = 2;
    private File phoneFile;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private String FinaUrl = "";
    // 工具栏
    private Toolbar toolbar;
    // 导航布局
    private TabLayout tabLayout;
    // 视图对象
    private ViewPager viewPager;
    // 自定义类，导航布局的适配器
    private TabAdaper tabAdaper;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] home_listview_text = {"添加帖子", "图片选择", "弹出窗口", "发送消息", "弹出通知栏", "小区电话通",
            "意见反馈", "设置"};
    ListView home_listview;
    ArrayList<HashMap<String, Object>> home_listview_item_data;
    private TextView tvName;
    private ImageViewPlus ivplu;
    private BmobFile icon;
    private DrawerLayout mDrawerLayout;
    private PopupWindow popup;
    private ArrayList<HashMap<String, Object>> lstImageItem;
    private String[] names = {"工作生活", "教育生活", "娱乐生活", "安全生活", "健康生活"};
    private Integer[] imgs = {R.drawable.work_life_icon,
            R.drawable.edu_life_icon, R.drawable.entertainment_life_icon,
            R.drawable.safety_life_icon, R.drawable.health_life_icon};
    private String data3;
    private BmobPushManager<BmobInstallation> bmobPushManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        data3 = getIntent().getStringExtra("fd");
//        System.out.println("接到跳转参数主页:" + data3);
//        Toast.makeText(MainActivity.this,"主页"+ data3, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);
        initView();
        setToolbar();
        setupViewPager();
        setupDrawer();
        showpop();
        setHead();


    }

    private void setHead() {
        ImageView IvMan = (ImageView) findViewById(R.id.iv_man);
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png";
//        new IamgeHelper(this).display(IvLoad,url);
//        new ImageHelper(this).display(IvMan,url);
        CopyOfJkUser newUser2 = BmobUser.getCurrentUser(CopyOfJkUser.class);
        if (newUser2.getHead() != null) {

            String im = newUser2.getHead().getFileUrl();
            new ImageHelper(this).display(ivplu, im);
            CommonSharedPreferences.setHead(im);
        } else {

//            new ImageHelper(this).display(ivplu, url);
        }
        TextView tvName = (TextView) findViewById(R.id.tv_username);
        if (BmobUser.getCurrentUser(JkUser.class).getUsername() != null) {
            tvName.setText(BmobUser.getCurrentUser(JkUser.class).getUsername());
        }
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        //设置抽屉的开关图标的功能和点击开关的监听事件
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description_format);//设置侧栏的开关闭合
        mDrawerToggle.syncState();//箭头和三道杠和抽屉保存同步
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void setupViewPager() {
        // 绑定viewpager与tablayout
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        // 新建适配器
        tabAdaper = new TabAdaper(getSupportFragmentManager());
        // 设置适配器
        viewPager.setAdapter(tabAdaper);
        // 直接绑定viewpager，消除了以前的需要设置监听器的繁杂工作
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setToolbar() {
        // 绑定对象
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //        toolbar.setLogo(R.mipmap.ic_launcher);
        // 标题的文字需在setSupportActionBar之前，不然会无效
//        toolbar.setTitle("主标题");
//        toolbar.setSubtitle("副标题");
        // 替换actionbar
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onResume() {
        super.onResume();
        // selectDB();
        try {
//            Toast.makeText(MainActivity.this,"主页"+ Conf.intentthis, Toast.LENGTH_SHORT).show();
//            if ((Conf.intentthis).equals("跳过来")) {
//            Toast.makeText(MainActivity.this, "主页" + Conf.getIntentThis(), Toast.LENGTH_SHORT).show();
            if ((Conf.getIntentThis()).equals("跳过来")) {
                viewPager.setCurrentItem(Conf.getToWhichPage());//选择某一页
                Conf.setIntentThis(null);
                System.out.println("跳了");
            } else {
                System.out.println("没跳");
//                viewPager.setCurrentItem(2);//选择某一页
            }

        } catch (Exception e) {

        }
    }

    private void initView() {

        RoundCornerImageView rcIv = (RoundCornerImageView) findViewById(R.id.foodItem_foodImg);
        ivplu = (ImageViewPlus) findViewById(R.id.ImageViewPlus);
        ivplu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeHead();
            }
        });


        Window window = getWindow();
        //设置状态栏颜色
//        window.setStatusBarColor((getResources().getColor(R.color.colorPrimary)));

        bmobPushManager = new BmobPushManager<BmobInstallation>();
        home_listview_item_data = new ArrayList<HashMap<String, Object>>();
//        for (int i = 0; i < home_listview_imgs.length; i++) {
        for (int i = 0; i < home_listview_text.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("home_logo", home_listview_imgs[i]);
            map.put("home_text", home_listview_text[i]);
            home_listview_item_data.add(map);
        }
        home_listview = (ListView) findViewById(R.id.lv_left_menu);
        HomeListViewAdapter hlvAtapter = new HomeListViewAdapter(this,
                home_listview_item_data);
        home_listview.setAdapter(hlvAtapter);
//        home_listview.setOnItemClickListener(new ItemClickListener());
        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerToggle.onDrawerClosed(mDrawerLayout);//点击后侧栏关闭
//                Toast.makeText(MainActivity.this, home_listview_text[position], Toast.LENGTH_SHORT).show();
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, AddComm.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, SelectPic.class);
                        startActivity(intent);
                        break;
                    case 2:
                        popup.showAsDropDown(toolbar);

                        break;
                    case 3:
                        // 创建Installation表的BmobQuery对象
                        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
                        // 并添加条件为设备类型属于android
                        query.addWhereEqualTo("deviceType", "android");
                        // 设置推送条件给bmobPushManager对象。
                        bmobPushManager.setQuery(query);
                        // 设置推送消息，服务端会根据上面的查询条件，来进行推送这条消息
                        bmobPushManager.pushMessage("这是一条#推送给所有Android设备的消息。");

                        break;
                    case 4:
                        Intent mintent = new Intent("cn.bmob.push.action.MESSAGE");
                        mintent.putExtra("msg", "会跳转的标题#这个是内容内容#1#管不了");
                        sendBroadcast(mintent);
                        break;
                }
                //关闭侧边栏
                mDrawerLayout.closeDrawers();

            }
        });

    }


    // fragment的适配器类
    class TabAdaper extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        // 标题数组
        String[] titles = {"新闻", "游戏", "科技"};

        public TabAdaper(FragmentManager fm) {
            super(fm);
            fragmentList.add(new NewsFragment());
            fragmentList.add(new GameFragment3());
//            fragmentList.add(new GameFragment2());
//            fragmentList.add(new GameFragment());
            fragmentList.add(new TechnologyFragment());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    //更改头像
    private void ChangeHead() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
        builder2.setTitle("设置头像");
        builder2.setMessage("选择");
        builder2.setNegativeButton("相机",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Intent cameraIntent = new Intent(
                        // "android.media.action.IMAGE_CAPTURE");
                        // // 拍照
                        // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        // getImageUri());
                        // cameraIntent.putExtra(
                        // MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        // startActivityForResult(cameraIntent,
                        // CAMERA_REQUEST_CODE);

                        if (isSdcardExisting()) {
                            Intent cameraintent = new Intent(
                                    "android.media.action.IMAGE_CAPTURE");
                            // 拍照
                            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    getImageUri());
                            cameraintent.putExtra(
                                    MediaStore.EXTRA_VIDEO_QUALITY, 0);
                            startActivityForResult(cameraintent,
                                    CAMERA_REQUEST_CODE);
                        } else {
                            LongToast("请插入sd卡");
                        }
                    }
                });
        builder2.setPositiveButton("图库",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Intent getAlbum = new
                        // Intent(Intent.ACTION_GET_CONTENT);
                        // Intent getAlbum = new Intent(Intent.ACTION_PICK);
                        //
                        // getAlbum.setType("image/*");
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, IMAGE_REQUEST_CODE);
                    }

                });
        builder2.create().show();
    }

    private boolean isSdcardExisting() {// 判断SD卡是否存在
        final String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private Uri getImageUri() {// 获取路径
        phoneFile = new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME);
        return Uri.fromFile(phoneFile);
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void resizeImage(Uri uri) {// 重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);

        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {// 显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Uri originalUri = data.getData();// 获取图片uri

            Drawable drawable = new BitmapDrawable(photo);
            ivplu.setImageDrawable(drawable);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri originalUri = data.getData();// 获取图片uri

                    // 选择图片成功，就显示图片
                    Bitmap bmpp = BitmapFactory.decodeFile(getRealFilePath(this,
                            originalUri));
                    ivplu.setImageBitmap(bmpp);
//                    FinaUrl = getRealFilePath(this, originalUri);
                    upload(getRealFilePath(this, originalUri));
                    // showToast(img_url);
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                        String[] imgs = {MediaStore.Images.Media.DATA};
                        // 将图片URI转换成存储路径
                        @SuppressWarnings("deprecation")
                        // Cursor cursor1 = this.managedQuery(getImageUri(), imgs,
                                Cursor cursor1 = this.getContentResolver().query(
                                getImageUri(), imgs, null, null, null);
                        int index1 = cursor1
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor1.moveToFirst();
                        String img_url1 = cursor1.getString(index1);
                        FinaUrl = img_url1;

                    } else {
                        LongToast("未找到存储卡，无法存储照片！");
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
                // case RESIZE_REQUEST_CODE:
                // if (data != null) {
                // showResizeImage(data);
                // }
                // break;
            }

        }

    }

    public void upload(final String imgpath) {

        File file = new File(imgpath);
        icon = new BmobFile(file);

        icon.uploadblock(new UploadFileListener() {
                             @Override
                             public void done(BmobException e) {
                                 if (e == null) {
                                     LongToast("上传成功");// 上传完成后保存到Person表
                                     JkUser user = BmobUser.getCurrentUser(JkUser.class);
                                     CopyOfJkUser newUser = new CopyOfJkUser();

                                     newUser.setHead(icon);
                                     newUser.update(user.getObjectId(), new UpdateListener() {
                                         @Override
                                         public void done(BmobException e) {

                                         }
                                     });
                                 } else {

                                 }
                             }
                         }
        );
//        newUser.setHead(new BmobFile(file));
//            newUser.setInfo("很开心的羊");
//            BmobUser bmobUser = BmobUser.getCurrentUser();
//            newUser.update(bmobUser.getObjectId(),new
//
//            UpdateListener() {
//                @Override
//                public void done (BmobException e){
//                    if (e == null) {
//                        toast("更新用户信息成功");
//                    } else {
//                        toast("更新用户信息失败:" + e.getMessage());
//                        System.out.println(e.getMessage() + "======");
//                    }
//                }
//            }
//
//            );
    }

    private void showpop() {
        lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imgs[i]);// 添加图像资源的ID
            map.put("ItemText", names[i]);// 按序号做ItemText
            lstImageItem.add(map);
        }
        LinearLayout gvPopupWindow = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_window, null);
        //LinearLayout assistant_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.assistant_layout, null);
        GridView pop_gridview = (GridView) gvPopupWindow.findViewById(R.id.pop_gridview);
        SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem, R.layout.pop_grid_view_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.pop_ItemImage, R.id.pop_ItemText});
        pop_gridview.setAdapter(saImageItems);
        pop_gridview.setOnItemClickListener(MainActivity.this);
        //pop_gridview.setOnKeyListener(this);
        popup = new PopupWindow(gvPopupWindow, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View pop_bg = (View) gvPopupWindow.findViewById(R.id.pop_bg);
        pop_bg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popup.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popup.dismiss();
//        Toast.makeText(MainActivity.this, "弹出狂", Toast.LENGTH_SHORT).show();
    }

}
