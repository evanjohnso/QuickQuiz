package com.evanrjohnso.quickquiz.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.util.ItemTouchHelperAdapter;
import com.evanrjohnso.quickquiz.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FirebaseWordListAdapter extends FirebaseRecyclerAdapter<String, FirebaseWordViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<String> mWordList = new ArrayList<>();


    public FirebaseWordListAdapter (Class<String> model,
                                    int modelLayout,
                                    Class<FirebaseWordViewHolder> viewHolderClass,
                                    Query ref,
                                    OnStartDragListener onStartDragListener,
                                    Context context) {
        super(model, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mWordList.add(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseWordViewHolder viewHolder, String model, int position) {
        viewHolder.bindWord(model);
        viewHolder.imageViewForClickListenerForDragGestures.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mWordList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mWordList.remove(position);
        getRef(position).removeValue();
        Toast.makeText(mContext, "Word Completed!", Toast.LENGTH_SHORT).show();
    }
}
