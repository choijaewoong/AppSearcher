package com.example.androidchoi.appsearcher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.appsearcher.Adapter.BoardListAdapter;
import com.example.androidchoi.appsearcher.Model.PostData;
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

        mBoardListAdapter.addItems(new PostData("카카오톡", "", "최재웅", "좋네요... \n 굿굿", "2016.06.17"));
        mBoardListAdapter.addItems(new PostData("카카오톡", "", "최재웅", "좋네요... \n 굿굿", "2016.06.17"));

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
