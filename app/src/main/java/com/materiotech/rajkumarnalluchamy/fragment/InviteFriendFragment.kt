package com.materiotech.rajkumarnalluchamy.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.appinvite.AppInviteInvitation
import com.materiotech.rajkumarnalluchamy.R

class InviteFriendFragment :Fragment(){


    companion object {
        private const val REQUEST_INVITE = 101
        fun newInstance(): InviteFriendFragment =
            InviteFriendFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(
            R.layout.fragment_refer_it,
            container, false)
        getWidget(rootView)
        return rootView
    }
    fun getWidget(rootView: View){
        onInviteClicked()
    }

    private fun onInviteClicked() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
            .setMessage(getString(R.string.invitation_message))
            /*.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
            .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))*/
            .setCallToActionText(getString(R.string.invitation_cta))
            .build()
        startActivityForResult(intent, REQUEST_INVITE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("InviteFriendFragment", "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the invitation IDs of all sent messages
//                val ids = AppInviteInvitation.getInvitationIds(resultCode, data!!)
//                for (id in ids) {
//                    Log.d("InviteFriendFragment", "onActivityResult: sent invitation $id")
//                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }
}