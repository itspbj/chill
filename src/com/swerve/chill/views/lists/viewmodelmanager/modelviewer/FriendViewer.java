package com.swerve.chill.views.lists.viewmodelmanager.modelviewer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.swerve.chill.ParseServiceAccessor;
import com.swerve.chill.R;
import com.swerve.chill.model.local.ChillManager;
import com.swerve.chill.model.local.ChillPreference;
import com.swerve.chill.model.local.chills.ChillDefinition;
import com.swerve.chill.model.remote.User;
import com.swerve.chill.views.contracts.ModelViewer;
import com.swerve.chill.views.contracts.ServiceAccessor;

public class FriendViewer implements ModelViewer<User> {
    private ServiceAccessor mServiceAccessor;
    public FriendViewer () {
        mServiceAccessor = new ParseServiceAccessor();
    }

    private class ViewHolder {
        Button username;
        ImageView usernameAliasImage;
        TextView usernameAlias;
    }

    @Override
    public View getModelView(final User item, View convertView, ViewGroup parent, final Context context, final ChillManager chillManager) {
        final ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_view_friend_item, parent, false);
            // Locate the TextViews in listview_item.xml
            holder.username = (Button) convertView.findViewById(R.id.username);
            holder.usernameAlias = (TextView) convertView.findViewById(R.id.textview_username_alias);
            holder.usernameAliasImage = (ImageView) convertView.findViewById(R.id.image_name);
            setTextColor(holder, chillManager);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChillPreference chillPreference = chillManager.getChillPreference();
                final String chillId = chillPreference.getChill().getId();
                final String location = chillPreference.getLocation();
                final String date = chillPreference.getDate();
                final MessageFriendTask messageFriendTask = new MessageFriendTask(
                        item,
                        chillId,
                        location,
                        date,
                        context);
                messageFriendTask.execute();
            }
        });
        holder.username.setText(item.getUsername());
        holder.usernameAlias.setText(item.getUsername().substring(0, 1));
        return convertView;
    }


    private class MessageFriendTask extends AsyncTask<Void, Void, Void> {
        private User friend;
        private String chillId;
        private String location;
        private String date;
        private Context context;

        public MessageFriendTask(final User friend, final String chillId, final String location, final String date, final Context context){
            this.friend = friend;
            this.chillId = chillId;
            this.location = location;
            this.date = date;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mServiceAccessor.sendMessage(friend, chillId, location, date);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            toast("MESSAGE SENT", context);
        }
    }

    private void toast (final String message, final Context context){
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setTextColor(final ViewHolder holder, final ChillManager chillManager) {
        final ChillDefinition definition = chillManager.getChillDefinition(chillManager.getChillPreference().getChill().getId());
        final int color = 0xff000000 + Integer.parseInt(Integer.toHexString(definition.getColor()), 16);
        holder.username.setTextColor(color);
        holder.usernameAlias.setTextColor(color);
        holder.usernameAliasImage.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}