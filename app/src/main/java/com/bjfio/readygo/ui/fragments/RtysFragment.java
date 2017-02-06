package com.bjfio.readygo.ui.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;
import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.presenter.RtysListPresenter;
import com.bjfio.readygo.presenter.contract.RtysListContract;
import com.bjfio.readygo.ui.adapter.viewholder.RtysItemHolder;
import com.bjfio.readygo.utils.JumpUtil;
import com.bjfio.readygo.utils.PreUtils;
import com.bjfio.readygo.utils.ScreenUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/27.
 */
public class RtysFragment extends BaseFragment<RtysListPresenter> implements RtysListContract.View, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    public final static String PRE_NAME = "rtys_column";
    @BindView(R.id.recycler)
    EasyRecyclerView mRecyclerView;

    RecyclerArrayAdapter<Task> mAdapter;
    GridLayoutManager gridLayoutManager;

    int column = 3;

    @OnClick(R.id.fab)
    public void action() {
        if (column == 3) {
            column = 2;
        } else {
            column = 3;
        }

        int firstVisible = gridLayoutManager.findFirstVisibleItemPosition();
        PreUtils.putInt(mContext, PRE_NAME, column);

        initView(null);
        initEvent();
        mRecyclerView.scrollToPosition(firstVisible);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_list;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    protected void initView(LayoutInflater inflater) {

        column = PreUtils.getInt(mContext, PRE_NAME, 3);
        if (mPresenter == null)
            mPresenter = new RtysListPresenter(this);
        if (mAdapter == null)
            mAdapter = new RecyclerArrayAdapter<Task>(mContext) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new RtysItemHolder(parent, column);
                }
            };

        FrameLayout frameLayout = new FrameLayout(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200,200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setNoMore(R.layout.view_nomore);

        mRecyclerView.setAdapterWithProgress(mAdapter);
        gridLayoutManager = new GridLayoutManager(mContext, column);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 3));
//        itemDecoration.setPaddingEdgeSide(true);
//        itemDecoration.setPaddingStart(true);
//        itemDecoration.setPaddingHeaderFooter(false);
//        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        mAdapter.setMore(R.layout.view_more, this);
        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = mAdapter.getItem(position);
                JumpUtil.go2BrowseImagesActivity(mContext, task.getUrl(),task.getImg());
            }
        });
        mRecyclerView.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        mPresenter.getData();
    }

    @Override
    public void showContent(List<Task> response) {
        if (response != null) {
            mAdapter.clear();
            if (response != null && response.size() < 10) {
                clearFooter();
            }
            mAdapter.addAll(response);
        }
    }

    @Override
    public void showMoreContent(List<Task> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void refreshFaild(String msg) {
        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mRecyclerView.showError();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    public void clearFooter() {
        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
    }
}
