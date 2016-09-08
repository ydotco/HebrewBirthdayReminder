package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    LayoutInflater inflater;
    List<Contact> contacts= Collections.emptyList();


    public ContactAdapter(Context context,List<Contact> contacts) {
        inflater = LayoutInflater.from(context);
        this.contacts=contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.tvName.setText(contact.fName+" "+contact.lName);
        holder.tvDate.setText(""+contact.hDay + " " + contact.hMonth);
        holder.imageView.setImageResource(R.drawable.birthday_image);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public void setFilter(List<Contact> contactModels) {
        contacts = new ArrayList<>();
        contacts.addAll(contactModels);
        notifyDataSetChanged();
    }

    public Contact removeItem(int position) {
        final Contact model = contacts.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Contact model) {
        contacts.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Contact model = contacts.remove(fromPosition);
        contacts.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Contact> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Contact> newModels) {
        for (int i = contacts.size() - 1; i >= 0; i--) {
            final Contact model = contacts.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Contact> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Contact model = newModels.get(i);
            if (!contacts.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Contact> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Contact model = newModels.get(toPosition);
            final int fromPosition = contacts.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
    class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName,tvDate;

        public ContactViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.ivContactItem);
            tvName= (TextView) itemView.findViewById(R.id.tvContactItem);
            tvDate= (TextView) itemView.findViewById(R.id.tvDateContactItem);
        }
    }
}
