package com.example.drawerlayout_fragment.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Adapter.CommAdatper;
import com.example.drawerlayout_fragment.Adapter.CursorAdatper3;
import com.example.drawerlayout_fragment.Adapter.GameAdapter;
import com.example.drawerlayout_fragment.Custom.ItemClickListener;
import com.example.drawerlayout_fragment.DB.MyDBOpenHelper;
import com.example.drawerlayout_fragment.JavaBean.Post2;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.Conf;
import com.example.drawerlayout_fragment.config.PullDownListView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2016/9/20.
 * 将图片转换为String数组，并用‘，’号隔开，存入数据库，后面取出来时再加以分割还原成List；
 */
public class GameFragment3 extends Fragment implements AbsListView.OnScrollListener {
    private int height;
    private int itemHeight;

    private List<Post2> post_down = new ArrayList<Post2>();
    private View view;
    private Context mContext;
    private CommAdatper ca;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private FloatingActionButton fab_reset;
    private Animation rotate;
    private ListView lv;
    private PullDownListView mPullDownView;
    private Handler handler;
    private CursorAdatper3 cursoradapter3;
    private Handler mHandler = new Handler();
    PullToRefreshListView mPullToRefreshView;
    private ILoadingLayout loadingLayout;
    ListView mMsgListView;
    private int count = 4;
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多

    private int limit = 4; // 每页的数据是10条
    private int curPage = 0; // 当前页的编号，从0开始
    private GameAdapter gameadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game3, null);
        mContext = this.getActivity();
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("创建了一次", "onActivityCreated_____________");
        queryData(0, STATE_REFRESH);
        initListView();

    }
    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume");
        //刷新时判断是否为从通知点进来的
        if (Conf.getFindTitle() != null) {
            queryData(0, STATE_REFRESH);
        }
    }

    private void initListView() {
        mPullToRefreshView = (PullToRefreshListView) view.findViewById(R.id.reflist);
        loadingLayout = mPullToRefreshView.getLoadingLayoutProxy();


        mMsgListView = mPullToRefreshView.getRefreshableView();
        gameadapter = new GameAdapter(mContext, post_down);
        mMsgListView.setAdapter(gameadapter);




        fab_reset = (FloatingActionButton) view.findViewById(R.id.fab_reset);
        // 加载动画
        rotate = AnimationUtils.loadAnimation(mContext, R.anim.widget_rotate);
// 动画执行完后停留在执行完的状态
        rotate.setFillAfter(false);
// 执行动画
        fab_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("点击了炫富按钮");
//                Toast.makeText(mContext, "点击了炫富按钮", Toast.LENGTH_SHORT).show();

                /** 开始动画 */
                fab_reset.startAnimation(rotate);
                queryData(0, STATE_REFRESH);
            }
        });


        loadingLayout.setLastUpdatedLabel("");
        loadingLayout
                .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
        loadingLayout
                .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
        loadingLayout
                .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
        // //滑动监听
        mPullToRefreshView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_top_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_top_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_top_release));
                } else if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
                }
            }
        });

        // 下拉刷新监听
        mPullToRefreshView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 下拉刷新(从第一页开始装载数据)
                        queryData(0, STATE_REFRESH);
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 上拉加载更多(加载下一页数据)
                        queryData(curPage, STATE_MORE);
                    }
                });

//        mMsgListView = mPullToRefreshView.getRefreshableView();
//        // 再设置adapter
//        mMsgListView.setAdapter(new GameAdapter(mContext, post_down));
    }


    /**
     * 分页获取数据
     *
     * @param
     * @param actionType ListView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryData(int page, final int actionType) {
        Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
                + actionType);

        BmobQuery<Post2> query = new BmobQuery<>();
        //如果是查询摸个标题
        if (Conf.getFindTitle()!= null) {
            query.addWhereEqualTo("title",Conf.getFindTitle());
            Conf.setFindTitle(null);
        }
        // 按时间降序查询
        query.order("-createdAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        // 如果是加载更多
        if (actionType == STATE_MORE) {
            query.setSkip(page * count);
            System.out.println("去掉页数" + page + "条目" + count + "=" + (page * count + 1));
        } else {
            page = 0;
            query.setSkip(page);
            // query.setSkip(0);
        }
        // 设置每页数据个数
        query.setLimit(count);

        // 查找数据
        query.findObjects(new FindListener<Post2>() {
            @Override
            public void done(List<Post2> list, BmobException e) {
                if (e == null) {
                    System.out.println("得到数据:" + list.size() + "条");
                    if (list.size() > 0) {
                        if (actionType == STATE_REFRESH) {
                            // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                            curPage = 0;
                            post_down.clear();
                            // 获取最后时间
//                            lastTime = list.get(list.size() - 1).getCreatedAt();
                        }

                        // 将本次查询的数据添加到bankCards中
                        for (Post2 td : list) {
                            post_down.add(td);
                            System.out.println("得到数据");
                        }

                        // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                        curPage++;
                        // showToast("第"+(page+1)+"页数据加载完成");
                    } else if (actionType == STATE_MORE) {
                        showToast("没有更多数据了");
                    } else if (actionType == STATE_REFRESH) {
                        showToast("没有数据");
                    }
                    mPullToRefreshView.onRefreshComplete();
//                    mMsgListView.setAdapter(new GameAdapter(mContext, post_down));
                    mMsgListView.setSelection(curPage * count - 1);
                    gameadapter.notifyDataSetChanged();
                } else {
                    showToast("查询失败:" + e);
                    mPullToRefreshView.onRefreshComplete();
                }


            }

        });
    }

    public void onDestroy() {

        super.onDestroy();
        Log.d("yes_______", "onDestroy");
    }


    private void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    //重写的的点击,adapter回调
    private void adapterClick() {
        gameadapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                showToast("点击了:" + postion);
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }

            @Override
            public void onItemSubViewClick(View view, int postion) {
//                ViewPage.toViewPage(mContext, postion, post_down.get(postion), post_down);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        //将所有View的透明度设置为1
        for(int i = 0; i < view.getChildCount(); i++){
            view.getChildAt(i).setAlpha(1);
            view.getChildAt(i).setScaleX(1);
            view.getChildAt(i).setScaleY(1);
        }
        //得到第一个可见的View
        View v = view.getChildAt(0);
        if(v != null){
            //得到这个v的高度
            itemHeight = v.getHeight();
            //得到可见部分
            int visiableLength = v.getBottom();
            //得到可见不分部分比例
            float ratio = visiableLength * 1.0f / itemHeight;
            v.setAlpha(ratio);
            v.setScaleX(ratio);
            v.setScaleY(ratio);


        }
        //得到最后一个可见的View
        v = view.getChildAt(visibleItemCount - 1);
        if(v != null){
            //得到这个v的高度
            itemHeight = v.getHeight();
            //得到可见部分
            int visiableLength = height - v.getTop();
            //得到可见不分部分比例
            float ratio = visiableLength * 1.0f / itemHeight;
            v.setScaleX(ratio);
            v.setScaleY(ratio);
            v.setAlpha(ratio);
        }
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        height = View.MeasureSpec.getSize(heightMeasureSpec);
    }
}
