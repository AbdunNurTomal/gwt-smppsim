package com.gtl.fonecta.client.handler;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gtl.fonecta.client.DataService;
import com.gtl.fonecta.client.DataServiceAsync;
import com.gtl.fonecta.client.GwtSmppSim;

/**
 * @author devang
 * 
 */
public class MessageHandler implements ClickHandler {

	GwtSmppSim gwtSMPPSim;

	/**
	 * Default constructor
	 */
	public MessageHandler() {
	}

	/**
	 * Parameterised constructor
	 * 
	 * @param gwtSMPPSim
	 */
	public MessageHandler(GwtSmppSim gwtSMPPSim) {
		this.gwtSMPPSim = gwtSMPPSim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	
	@Override
	public void onClick(ClickEvent event) {

		Long handsetNum = Long.parseLong(gwtSMPPSim.getHansetNum().getText());
		Long serviceNum = Long.parseLong(gwtSMPPSim.getServiceNum().getText());
		String shortMessage = gwtSMPPSim.getTextMessage().getText();
		Date now = new Date();
		Timestamp timestamp = new Timestamp(now.getTime());

		DataServiceAsync serviceProxy;
		serviceProxy = GWT.create(DataService.class);
		serviceProxy.insertMessage(handsetNum.toString(),
				serviceNum.toString(), shortMessage, timestamp,
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.getStackTrace();
					}
				});
		
		//String host = gwtSMPPSim.getHiddenHost().getValue();
		//int port = Integer.parseInt(gwtSMPPSim.getHiddenHttpPort().getValue());
		String host = "localhost";
		if(gwtSMPPSim.getHiddenHost().getValue().length()>0){
			host=gwtSMPPSim.getHiddenHost().getValue();
		}
		int port = 9999;
		if(gwtSMPPSim.getHiddenHttpPort().getValue().length()>0){
			port =Integer.parseInt(gwtSMPPSim.getHiddenHttpPort().getValue()); 
		}

		String url = "http://"
				+ host
				+ ":"
				+ port
				+ "/inject_mo?short_message="
				+ shortMessage
				+ "&source_addr="
				+ handsetNum
				+ "&destination_addr="
				+ serviceNum
				+ "&submit=Submit+Message&service_type=&source_addr_ton=1&source_addr_npi=1&dest_addr_ton=1&dest_addr_npi=1&esm_class=0&protocol_ID=&priority_flag=&registered_delivery_flag=0&data_coding=0&user_message_reference=&source_port=&destination_port=&sar_msg_ref_num=&sar_total_segments=&sar_segment_seqnum=&user_response_code=&privacy_indicator=&payload_type=&message_payload=&callback_num=&source_subaddress=&dest_subaddress=&language_indicator=&tlv1_tag=&tlv1_len=&tlv1_val=&tlv2_tag=&tlv2_len=&tlv2_val=&tlv3_tag=&tlv3_len=&tlv3_val=&tlv4_tag=&tlv4_len=&tlv4_val=";
		redirect(url);

	}

	native void redirect(String url)
	/*-{
		mainurl=$wnd.location.href;
		$wnd.location.replace(url);	        									
		$wnd.location.replace(mainurl);
	}-*/;

	/**
	 * @return the gwtSMPPSim
	 */
	public GwtSmppSim getGwtSMPPSim() {
		return gwtSMPPSim;
	}

	/**
	 * @param gwtSMPPSim
	 *            the gwtSMPPSim to set
	 */
	public void setGwtSMPPSim(GwtSmppSim gwtSMPPSim) {
		this.gwtSMPPSim = gwtSMPPSim;
	}
}
