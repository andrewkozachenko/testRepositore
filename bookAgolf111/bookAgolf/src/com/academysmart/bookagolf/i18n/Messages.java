package com.academysmart.bookagolf.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.academysmart.bookagolf.i18n.messages"; //$NON-NLS-1$
	public static String AddOrderView_AddOrder;
	public static String AddOrderView_ChooseField;
	public static String AddOrderView_Date;
	public static String AddOrderView_Holes;
	public static String AddOrderView_MoneyPaid;
	public static String AddOrderView_NoGameSelected;
	public static String AddOrderView_Warning;
	public static String AddOrderViewListEquipment_Amount;
	public static String AddOrderViewListEquipment_Equipment;
	public static String AddOrderViewListEquipment_Price;
	public static String AddOrderViewListEquipment_Subtotal;
	public static String AddOrderViewListGame_SubTotal;
	public static String CalendarMouseListener_title;
	public static String CompanyInfo_add_photo;
	public static String CompanyInfo_AddPhoto;
	public static String CompanyInfo_Address;
	public static String CompanyInfo_City;
	public static String CompanyInfo_Country;
	public static String CompanyInfo_Region;
	public static String CompanyInfo_Description;
	public static String CompanyInfo_email;
	public static String CompanyInfo_ShortDescription;
	//TODO Remove this string
	public static String CompanyInfo_Golfcar;
	public static String CompanyInfo_Holes;
	//TODO Remove this string
	public static String CompanyInfo_Hotel;
	public static String CompanyInfo_Latitude;
	public static String CompanyInfo_Longitude;
	public static String CompanyInfo_Name;
	public static String CompanyInfo_Par;
	public static String CompanyInfo_Phone;
	public static String CompanyInfo_Save;
	public static String CompanyInfo_URL;
	public static String ConnectionStatusItem_API;
	public static String ConnectionStatusItem_Authorization;
	public static String ConnectionStatusItem_Internet;
	public static String ConnectionStatusItem_NoInternet;
	public static String DetailsEditingSupport_title;
	public static String GameUpdateEditingSupport_title;
	public static String GameUpdateEditingSupport_update;
	public static String MainWindow_addOrder;
	public static String MainWindow_this_status;
	public static String MainWindow_ordersTab_text;
	public static String MainWindow_ordersTab_toolTipText;
	public static String MainWindow_group_text;
	public static String MainWindow_radioList_text;
	public static String MainWindow_radioCalendar_text;
	public static String MainWindow_outOf_text;
	public static String MainWindow_prices_text;
	public static String MainWindow_prices_toolTipText;
	public static String MainWindow_title_one_text;
	public static String MainWindow_timeStartLabel_text;
	public static String MainWindow_timeEndLabel_text;
	public static String MainWindow_mondayButton_text;
	public static String MainWindow_tuesdayButton_text;
	public static String MainWindow_wednesdayButton_text;
	public static String MainWindow_thursdayButton_text;
	public static String MainWindow_fridayButton_text;
	public static String MainWindow_saturdayButton_text;
	public static String MainWindow_sundayButton_text;
	public static String MainWindow_Days_text;
	public static String MainWindow_labelFieldName_text;
	public static String MainWindow_golfCar_text;
	public static String MainWindow_trolley_text;
	public static String MainWindow_club_text;
	public static String MainWindow_labelNumberOfPlayers_text;
	public static String MainWindow_labelPrice_text;
	public static String MainWindow_About;
	public static String MainWindow_Add_text;
	public static String MainWindow_AmpAbout;
	public static String MainWindow_AmpHelp;
	public static String MainWindow_title_two_text;
	public static String MainWindow_timeHire_text;
	public static String MainWindow_mondayHire_text;
	public static String MainWindow_tuesdayHire_text;
	public static String MainWindow_wednesdayHire_text;
	public static String MainWindow_thursdayHire_text;
	public static String MainWindow_fridayHire_text;
	public static String MainWindow_saturdayHire_text;
	public static String MainWindow_sundayHire_text;
	public static String MainWindow_daysHire_text;
	public static String MainWindow_accessory_text;
	public static String MainWindow_number_text;
	public static String MainWindow_buttonAddHire_text;
	public static String MainWindow_about_text;
	public static String MainWindow_about_toolTipText;
	public static String MainWindow_CallCalendarToolTip;
	public static String MainWindow_Choose;
	public static String MainWindow_ChooseDate;
	public static String MainWindow_Help;
	public static String MainWindow_Next;
	public static String MainWindow_Previous;
	public static String MainWindow_WindowTitle;
	public static String Order_additionalEquipment;
	public static String Order_amount;
	public static String Order_confirmed;
	public static String Order_createdAt;
	public static String Order_date;
	public static String Order_end;
	public static String Order_equipment;
	public static String Order_equipmentPrice;
	public static String Order_golfField;
	public static String Order_id;
	public static String Order_orderedGames;
	public static String Order_paid;
	public static String Order_pending;
	public static String Order_price;
	public static String Order_start;
	public static String Order_status;
	public static String Order_StatusApproved;
	public static String Order_StatusConfirmed;
	public static String Order_StatusDeclinedByClub;
	public static String Order_StatusDeclinedByUser;
	public static String Order_StatusPending;
	public static String Order_user;
	public static String OrderViewList_Choose_date;
	public static String OrderViewList_Confirm;
	public static String OrderViewList_Date_created;
	public static String OrderViewList_Date_order;
	public static String OrderViewList_Details;
	public static String OrderViewList_Enter_name;
	public static String OrderViewList_Filter;
	public static String OrderViewList_Name;
	public static String OrderViewList_Players;
	public static String OrderViewList_Total;
	public static String PricesViewListGame_clients;
	public static String PricesViewListGame_delete;
	public static String PricesViewListGame_edit;
	public static String PricesViewListGame_Field;
	public static String PricesViewListGame_Fri;
	public static String PricesViewListGame_Mon;
	public static String PricesViewListGame_Players;
	public static String PricesViewListGame_Price;
	public static String PricesViewListGame_Sat;
	public static String PricesViewListGame_Sun;
	public static String PricesViewListGame_Thu;
	public static String PricesViewListGame_Time;
	public static String PricesViewListGame_Tue;
	public static String PricesViewListGame_Wed;
	public static String PricesViewListHire_Equipment;
	public static String PricesViewListHire_Pieces;
	public static String PricesViewListHire_Price;
	public static String PricesViewListHire_Time;
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
