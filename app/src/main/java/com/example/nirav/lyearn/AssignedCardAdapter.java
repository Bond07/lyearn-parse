package com.example.nirav.lyearn;

/**
 * Created by n on 04/02/15.
 */
        import android.content.Context;
        import android.os.Handler;
        import android.os.Looper;
        import android.provider.ContactsContract;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import java.util.ArrayList;


public class AssignedCardAdapter extends RecyclerView.Adapter<AssignedCardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<AssignedCards> mAssignedCardsArrayList;

    public AssignedCardAdapter(ArrayList<AssignedCards> cardsArrayList) {

        this.mAssignedCardsArrayList = cardsArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_card, parent, false);

       // view.setOnClickListener(CardFragment.);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView cardTitle = holder.cardTitle;
        TextView cardAssigner = holder.cardAssigner;

        cardTitle.setText(mAssignedCardsArrayList.get(listPosition).getCardTitle());
        cardAssigner.setText(mAssignedCardsArrayList.get(listPosition).getCardAssigner());
    }

    public static void runOnUiThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler .post(runnable);
    }

    @Override
    public int getItemCount() {
        return mAssignedCardsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cardTitle;
        private TextView cardAssigner;
        public static View.OnClickListener cardOnClickListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            this.cardAssigner = (TextView) itemView.findViewById(R.id.card_assigner);
        }
    }
}

