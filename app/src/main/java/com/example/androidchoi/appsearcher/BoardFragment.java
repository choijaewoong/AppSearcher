package com.example.androidchoi.appsearcher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Adapter.BoardListAdapter;
import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.PostList;
import com.github.clans.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    RecyclerView mRecyclerView;
    BoardListAdapter mBoardListAdapter;
    FloatingActionButton mFloatingActionButton;

    public BoardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        setHasOptionsMenu(true);

        // RecyclerVIew, BoardListAdapter 세팅
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_board_list);
        mBoardListAdapter = new BoardListAdapter();
        mRecyclerView.setAdapter(mBoardListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        showPostList(); // 게시글 목록 불러오기
//        mBoardListAdapter.addItems(new PostData("카카오톡", "", "최재웅", "좋네요... \n 굿굿", 20160617));
//        mBoardListAdapter.addItems(new PostData("카카오톡", "", "최재웅", "좋네요... \n 굿굿", 20160617));

        // 게시글 작성 플로팅 버튼
        mFloatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_write_post);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 작성화면으로 이동
            }
        });
        return view;
    }

    // 게시글 요청 메소드
    public void showPostList(){
        NetworkManager.getInstance().showPostList(new NetworkManager.OnResultListener<PostList>() {
            @Override
            public void onSuccess(PostList result) {
                mBoardListAdapter.setItems(result.getPostList());
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "목록을 가져오지 못하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
