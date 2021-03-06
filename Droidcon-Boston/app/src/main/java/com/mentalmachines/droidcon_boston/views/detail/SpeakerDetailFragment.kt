package com.mentalmachines.droidcon_boston.views.detail

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mentalmachines.droidcon_boston.R
import com.mentalmachines.droidcon_boston.data.FirebaseDatabase.SpeakerEvent
import com.mentalmachines.droidcon_boston.firebase.FirebaseHelper
import com.mentalmachines.droidcon_boston.utils.ServiceLocator.Companion.gson
import com.mentalmachines.droidcon_boston.utils.getHtmlFormattedSpanned
import com.mentalmachines.droidcon_boston.utils.loadUriInCustomTab
import com.mentalmachines.droidcon_boston.views.transform.CircleTransform
import kotlinx.android.synthetic.main.speaker_detail_fragment.imgv_linkedin
import kotlinx.android.synthetic.main.speaker_detail_fragment.imgv_speaker_detail_avatar
import kotlinx.android.synthetic.main.speaker_detail_fragment.imgv_twitter
import kotlinx.android.synthetic.main.speaker_detail_fragment.tv_speaker_detail_description
import kotlinx.android.synthetic.main.speaker_detail_fragment.tv_speaker_detail_designation
import kotlinx.android.synthetic.main.speaker_detail_fragment.tv_speaker_detail_name


class SpeakerDetailFragment : Fragment() {

    private val firebaseHelper = FirebaseHelper.instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.speaker_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemData = gson.fromJson(arguments.getString(SpeakerEvent.SPEAKER_ITEM_ROW), SpeakerEvent::class.java)
        populateView(itemData)
    }

    private fun populateView(itemData: SpeakerEvent) {
        tv_speaker_detail_name.text = itemData.name
        tv_speaker_detail_designation.text = String.format("%s \n@ %s", itemData.title, itemData.org)
        tv_speaker_detail_description.text = itemData.bio.getHtmlFormattedSpanned()

        val twitterHandle = itemData.socialProfiles?.get("twitter")
        if (!twitterHandle.isNullOrEmpty()) {
            imgv_twitter.setOnClickListener({
                activity.loadUriInCustomTab(String.format("%s%s", resources.getString(R.string.twitter_link), twitterHandle))
            })
        } else {
            imgv_twitter.visibility = View.GONE
        }


        val linkedinHandle = itemData.socialProfiles?.get("linkedIn")
        if (!linkedinHandle.isNullOrEmpty()) {
            imgv_linkedin.setOnClickListener({
                activity.loadUriInCustomTab(String.format("%s%s", resources.getString(R.string.linkedin_profile_link), linkedinHandle))
            })
        } else {
            imgv_linkedin.visibility = View.GONE
        }

        Glide.with(activity)
                .load(itemData.pictureUrl)
                .transform(CircleTransform(activity))
                .placeholder(R.drawable.emo_im_cool)
                .crossFade()
                .into(imgv_speaker_detail_avatar)

    }
}
