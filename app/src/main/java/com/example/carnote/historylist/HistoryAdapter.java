package com.example.carnote.historylist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carnote.GasTankUpActivity;
import com.example.carnote.R;
import com.example.carnote.model.TankUpRecord;

import java.text.DateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{
    private Context context;
    private List<TankUpRecord> tankList;
    private TankUpRecord tankUpRecord;
    private Drawable drawable;

    public HistoryAdapter(Context context, List<TankUpRecord> tankList)
    {
        this.context = context;
        this.tankList = tankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        tankUpRecord = tankList.get(position);
        drawable = context.getResources().getDrawable(R.drawable.ic_new_tankup);
        holder.activityImageView.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getDateInstance();
        holder.leftLabelTopTextView.setText(dateFormat.format(tankUpRecord.getTankUpDate()));
        holder.rightLabelTopTextView.setText(tankUpRecord.getMileage().toString() + " KM");
        holder.leftLabelBottomTextView.setText(tankUpRecord.getLiters()+" L");
        holder.rightLabelBottomTextView.setText(tankUpRecord.getCostInPLN()+ " PLN");
        holder.trashImageView.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                tankList.remove(position);
                HistoryAdapter.this.notifyDataSetChanged();
             }
         });
    }

    @Override
    public int getItemCount()
    {
        if (tankList == null)
        {
            return 0;
        }else {
            return tankList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView leftLabelBottomTextView;
        protected TextView leftLabelTopTextView;
        protected TextView rightLabelBottomTextView;
        protected TextView rightLabelTopTextView;
        protected ImageView trashImageView;
        protected ImageView activityImageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.activityImageView = (ImageView) itemView.findViewById(R.id.activity_imageview);
            this.trashImageView = (ImageView) itemView.findViewById(R.id.trash_image_view);

           this.leftLabelBottomTextView = (TextView) itemView.findViewById(R.id.left_label_bottom);
           this.leftLabelTopTextView = (TextView) itemView.findViewById(R.id.left_label_top);
           this.rightLabelBottomTextView = (TextView) itemView.findViewById(R.id.right_label_bottom);
           this.rightLabelTopTextView = (TextView) itemView.findViewById(R.id.right_label_top);
        }
    }
}
