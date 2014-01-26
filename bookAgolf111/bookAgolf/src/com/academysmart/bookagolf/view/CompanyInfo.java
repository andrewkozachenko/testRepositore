package com.academysmart.bookagolf.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Club;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.ImageProvider;
import com.academysmart.bookagolf.view.listener.IntegerVerifyListener;

public class CompanyInfo extends Composite {

	private final static int FIRST_CONTROL_WIDTH = 300;
	private final static int SECOND_CONTROL_WIDTH = 50;
	private final static int SMALL_CONTROL_HEIGHT = 20;
	private final static int LARGE_CONTROL_HEIGHT = 100;
	private final static int LABEL_WIDTH = 70;
	private final static int BUTTON_WIDTH = 100;
	private final static int BUTTON_HEIGHT = 30;
	private final static String NAME_LABEL = Messages.CompanyInfo_Name;

	private final static String COUNTRY_LABEL = Messages.CompanyInfo_Country;
	private final static String REGION_LABEL = Messages.CompanyInfo_Region;
	private final static String TOWN_LABEL = Messages.CompanyInfo_City;
	private final static String ADDRESS_LABEL = Messages.CompanyInfo_Address;
	private final static String DESCRIPTION_LABEL = Messages.CompanyInfo_Description;
	private final static String SHORT_DESCRIPTION_LABEL = Messages.CompanyInfo_ShortDescription;
	private final static String PHONEONE_LABEL = Messages.CompanyInfo_Phone;
	private final static String LATITUDE_LABEL = Messages.CompanyInfo_Latitude;
	private final static String LONGITUDE_LABEL = Messages.CompanyInfo_Longitude;
	// private final static String PHONETWO_LABEL =
	// Messages.CompanyInfo_PhoneCell;

	private final static String HOLECOUNT_LABEL = Messages.CompanyInfo_Holes;
	private final static String TWAINCOUNT_LABEL = Messages.CompanyInfo_Par;

	private final static String OPENPHOTO_LABEL = Messages.CompanyInfo_AddPhoto;
	private final static String SAVEINFO_LABEL = Messages.CompanyInfo_Save;

	public Text companyName;
	public Combo companyCountry;
	public Spinner companyLatitude;
	public Spinner companyLongitude;
	public Combo companyRegion;
	public Text companyCity;
	public Text companyAddress;
	public Text companyDescription;
	public Text companyShortDescription;
	public Text companyEmail;
	public Text companyPhoneOne;
	public Combo holeCount;
	public Text twainCount;
	public Gallery photoList;
	public Button openPhoto;
	public Button saveInfo;

	private Label companyNameLabel;
	private Label companyCountryLabel;
	private Label companyLatitudeLabel;
	private Label companyLongitudeLabel;
	private Label companyRegionLabel;
	private Label companyTownLabel;
	private Label companyAddressLabel;
	private Label companyDescriptionLabel;
	private Label companyShortDescriptionLabel;
	private Label companyPhoneOneLabel;
	private Label holeCountLabel;
	private Label twainCountLabel;
	private Label companyEmailLabel;

	private Club club;

	public CompanyInfo(Composite parentComposite) {

		super(parentComposite, SWT.NONE);

		club = ClubProvider.getClub();
		// int leftOffset, topOffset;

		// leftOffset = 2 * LEFT_OFFSET + FIRST_CONTROL_WIDTH;

		// topOffset = TOP_OFFSET;
		companyName = new Text(this, SWT.BORDER);
		companyName.setBounds(20, 20, 300, 20);
		companyNameLabel = new Label(this, SWT.NONE);
		companyNameLabel.setBounds(342, 20, LABEL_WIDTH, SMALL_CONTROL_HEIGHT);
		companyNameLabel.setText(NAME_LABEL);

		// topOffset = TOP_OFFSET;

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyCountry = new Combo(this, SWT.NONE);
		companyCountry.setBounds(20, 103, FIRST_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyCountryLabel = new Label(this, SWT.NONE);
		companyCountryLabel.setBounds(342, 106, LABEL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyCountryLabel.setText(COUNTRY_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyRegion = new Combo(this, SWT.NONE);
		companyRegion.setBounds(20, 60, FIRST_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		// String items[] = { "Europe", "Afrika", "Asia" };
		// companyRegion.setItems(items);
		// companyRegion.select(0);

		// companyRegion.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// if (companyRegion.getText().equals("Europe")) {
		// String newItems1[] = club.getEurope()
		// .toArray(new String[0]);
		// companyCountry.setItems(newItems1);
		// companyCountry.select(0);
		// companyCountry.setEnabled(true);
		// } else if (companyRegion.getText().equals("Asia")) {
		// String newItems2[] = club.getAsia().toArray(new String[0]);
		// companyCountry.setItems(newItems2);
		// companyCountry.select(0);
		// companyCountry.setEnabled(true);
		// } else if (companyRegion.getText().equals("Afrika")) {
		// String newItems3[] = club.getAfrika()
		// .toArray(new String[0]);
		// companyCountry.setItems(newItems3);
		// companyCountry.select(0);
		// companyCountry.setEnabled(true);
		// } else {
		// }
		// }
		// });

		companyRegionLabel = new Label(this, SWT.NONE);
		companyRegionLabel
				.setBounds(342, 63, LABEL_WIDTH, SMALL_CONTROL_HEIGHT);
		companyRegionLabel.setText(REGION_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyCity = new Text(this, SWT.BORDER);
		companyCity.setBounds(20, 152, FIRST_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyTownLabel = new Label(this, SWT.NONE);
		companyTownLabel.setBounds(342, 155, LABEL_WIDTH, SMALL_CONTROL_HEIGHT);
		companyTownLabel.setText(TOWN_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyAddress = new Text(this, SWT.BORDER);
		companyAddress.setBounds(20, 195, FIRST_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyAddressLabel = new Label(this, SWT.NONE);
		companyAddressLabel.setBounds(342, 195, LABEL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyAddressLabel.setText(ADDRESS_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyDescription = new Text(this, SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL | SWT.MULTI);
		companyDescription.setBounds(418, 152, 300, 108);
		companyDescriptionLabel = new Label(this, SWT.NONE);
		companyDescriptionLabel.setBounds(740, 155, LABEL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyDescriptionLabel.setText(DESCRIPTION_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		companyShortDescription = new Text(this, SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL | SWT.MULTI);
		companyShortDescription.setBounds(418, 20, 300, 106);
		companyShortDescriptionLabel = new Label(this, SWT.NONE);
		companyShortDescriptionLabel.setBounds(740, 23, 108, 20);
		companyShortDescriptionLabel.setText(SHORT_DESCRIPTION_LABEL);

		companyLatitude = new Spinner(this, SWT.BORDER);
		companyLatitude.setIncrement(1000000);
		companyLatitude.setMaximum(90000000);
		companyLatitude.setMinimum(-90000000);
		companyLatitude.setSelection(90000000);
		companyLatitude.setDigits(6);
		companyLatitude.setBounds(20, 238, 89, 22);
		companyLatitudeLabel = new Label(this, SWT.NONE);
		companyLatitudeLabel.setBounds(115, 238, 50, 20);
		companyLatitudeLabel.setText(LATITUDE_LABEL);

		companyLongitude = new Spinner(this, SWT.BORDER);
		companyLongitude.setIncrement(1000000);
		companyLongitude.setMaximum(180000000);
		companyLongitude.setMinimum(-180000000);
		companyLongitude.setSelection(180000000);
		companyLongitude.setDigits(6);
		companyLongitude.setBounds(20, 277, 89, 22);
		companyLongitudeLabel = new Label(this, SWT.NONE);
		companyLongitudeLabel.setBounds(115, 277, 50, 20);
		companyLongitudeLabel.setText(LONGITUDE_LABEL);

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;
		companyEmail = new Text(this, SWT.BORDER);
		companyEmail.setBounds(20, 443, 300, 20);
		companyEmailLabel = new Label(this, SWT.NONE);
		companyEmailLabel
				.setBounds(342, 443, LABEL_WIDTH, SMALL_CONTROL_HEIGHT);
		companyEmailLabel.setText(Messages.CompanyInfo_email);

		// topOffset += TOP_OFFSET + LARGE_CONTROL_HEIGHT;
		companyPhoneOne = new Text(this, SWT.BORDER);
		companyPhoneOne.setBounds(20, 407, FIRST_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyPhoneOneLabel = new Label(this, SWT.NONE);
		companyPhoneOneLabel.setBounds(342, 407, LABEL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		companyPhoneOneLabel.setText(PHONEONE_LABEL);

		// leftOffset = (5 * LEFT_OFFSET + FIRST_CONTROL_WIDTH + LABEL_WIDTH);

		// topOffset = TOP_OFFSET;
		twainCount = new Text(this, SWT.BORDER);
		twainCount.setBounds(571, 443, SECOND_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		twainCount.addVerifyListener(new IntegerVerifyListener());
		twainCountLabel = new Label(this, SWT.NONE);
		twainCountLabel.setBounds(648, 446, 70, 20);
		twainCountLabel.setText(TWAINCOUNT_LABEL);

		// topOffset = TOP_OFFSET;

		// topOffset = TOP_OFFSET;

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		// isGolfCar = new Button(this, SWT.CHECK);
		// isGolfCar.setBounds((leftOffset + 7 * LEFT_OFFSET), topOffset,
		// (LABEL_WIDTH + LEFT_OFFSET), SMALL_CONTROL_HEIGHT);
		// isGolfCar.setText(ISGOLFCAR_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;
		holeCount = new Combo(this, SWT.READ_ONLY);
		holeCount.setItems(new String[] { "9", "18" }); //$NON-NLS-1$ //$NON-NLS-2$
		holeCount.setBounds(418, 443, SECOND_CONTROL_WIDTH,
				SMALL_CONTROL_HEIGHT);
		holeCountLabel = new Label(this, SWT.NONE);
		holeCountLabel.setBounds(495, 443, LABEL_WIDTH, SMALL_CONTROL_HEIGHT);
		holeCountLabel.setText(HOLECOUNT_LABEL);

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;

		// topOffset += TOP_OFFSET + SMALL_CONTROL_HEIGHT;

		photoList = new Gallery(this, SWT.BORDER | SWT.H_SCROLL);
		photoList
				.setBounds(418, 281, FIRST_CONTROL_WIDTH, LARGE_CONTROL_HEIGHT);
		photoList.setGroupRenderer(new DefaultGalleryGroupRenderer());
		photoList.setItemRenderer(new DefaultGalleryItemRenderer());
		// GalleryItem photoGroup = new GalleryItem(photoList, SWT.None);
		// photoGroup.setExpanded(true);
		//		photoGroup.setText(""); //$NON-NLS-1$
		// List<com.academysmart.bookagolf.model.Image> clubImages =
		// ImageProvider.INSTANCE
		// .getImages();
		// for (com.academysmart.bookagolf.model.Image image : clubImages) {
		// GalleryItem photo = new GalleryItem(photoGroup, SWT.None);
		// photo.setImage(getImageByUrl(image.getUrl()));
		// }
		loadImages();

		// topOffset += (TOP_OFFSET / 2) + LARGE_CONTROL_HEIGHT;
		openPhoto = new Button(this, SWT.NONE);
		openPhoto.setBounds(618, 389, BUTTON_WIDTH, BUTTON_HEIGHT);
		openPhoto.setText(OPENPHOTO_LABEL);
		openPhoto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell dialog = new Shell(getShell(),
						SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
				dialog.setSize(400, 200);
				dialog.setText(Messages.CompanyInfo_add_photo);
				RowLayout layoutDialog = new RowLayout();
				layoutDialog.type = SWT.VERTICAL;
				dialog.setLayout(layoutDialog);

				Composite urlComposite = new Composite(dialog, SWT.NONE);
				urlComposite.setLayoutData(new RowData(-1, 29));
				GridLayout compositeLayout = new GridLayout();
				compositeLayout.numColumns = 3;
				urlComposite.setLayout(compositeLayout);
				final Text urlText = new Text(urlComposite, SWT.NONE);
				GridData gd_urlText = new GridData(SWT.LEFT, SWT.CENTER, false,
						false, 1, 1);
				gd_urlText.heightHint = 21;
				gd_urlText.widthHint = 327;
				gd_urlText.minimumWidth = 250;
				urlText.setLayoutData(gd_urlText);
				urlText.setBounds(urlText.getBounds().x, urlText.getBounds().y,
						250, 20);
				Label urlLabel = new Label(urlComposite, SWT.NONE);
				urlLabel.setText(Messages.CompanyInfo_URL);

				Button addButton = new Button(urlComposite, SWT.CENTER);
				addButton.setText(Messages.CompanyInfo_AddPhoto);
				addButton.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent event) {
						String pathToImage = urlText.getText();
						ImageProvider.INSTANCE.addImage(pathToImage);
						ImageProvider.INSTANCE.refreshImages();

						photoList.removeAll();
						// GalleryItem photoGroup = new GalleryItem(photoList,
						// SWT.None);
						// photoGroup.setExpanded(true);
						//						photoGroup.setText(""); //$NON-NLS-1$
						// List<com.academysmart.bookagolf.model.Image>
						// clubImages = ImageProvider.INSTANCE
						// .getImages();
						// for (com.academysmart.bookagolf.model.Image image :
						// clubImages) {
						// GalleryItem photo = new GalleryItem(photoGroup,
						// SWT.None);
						// photo.setImage(getImageByUrl(image.getUrl()));
						// }
						// photoList.redraw();
						loadImages();
						dialog.close();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// empty

					}
				});
				Point dialogSize = urlComposite.computeSize(SWT.DEFAULT, 65);
				dialogSize.x += 5;
				dialog.setSize(dialogSize);
				dialog.open();
			}
		});

		// topOffset = (companyPhoneTwo.getBounds().y
		// + companyPhoneTwo.getBounds().height + 2 * TOP_OFFSET);
		saveInfo = new Button(this, SWT.NONE);
		saveInfo.setBounds(729, 495, BUTTON_WIDTH, BUTTON_HEIGHT);
		saveInfo.setText(SAVEINFO_LABEL);

		saveInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Club club = ClubProvider.getClub();

				club.setName(companyName.getText());
				club.setDescription(companyDescription.getText());
				club.setShortDescription(companyShortDescription.getText());
				club.setCity(companyCity.getText());
				club.setRegion(companyRegion.getText());
				club.setCountry(companyCountry.getText());
				club.setAddress(companyAddress.getText());
				club.setLat((companyLatitude.getSelection())
						/ Math.pow(10, companyLatitude.getDigits()));
				club.setLng((companyLongitude.getSelection())
						/ Math.pow(10, companyLongitude.getDigits()));
				// club.setPhoneCell(companyPhoneTwo.getText());
				club.setPhone(companyPhoneOne.getText());
				club.seteMail(companyEmail.getText());
				club.setHolesList(Integer.parseInt(holeCount.getText()));
				club.setHitsToWin(Integer.parseInt(twainCount.getText()));
				// club.setCar(isGolfCar.getSelection());
				// System.out.println(club);
				ClubProvider.updateClub();

			}
		});

		if (club != null) {
			companyName.setText((club.getName() != null) ? club.getName() : ""); //$NON-NLS-1$
			companyDescription.setText((club.getDescription() != null) ? club
					.getDescription() : ""); //$NON-NLS-1$
			companyShortDescription
					.setText((club.getShortDescription() != null) ? club
							.getShortDescription() : ""); //$NON-NLS-1$
			companyCity.setText((club.getCity() != null) ? club.getCity() : ""); //$NON-NLS-1$
			companyRegion.setText((club.getRegion() != null) ? club.getRegion()
					: ""); //$NON-NLS-1$
			companyCountry.setText((club.getCountry() != null) ? club
					.getCountry() : ""); //$NON-NLS-1$
			companyAddress.setText((club.getAddress() != null) ? club
					.getAddress() : ""); //$NON-NLS-1$
			companyPhoneOne.setText((club.getPhone() != null) ? club.getPhone()
					: ""); //$NON-NLS-1$
			// companyPhoneTwo.setText((club.getPhoneCell() != null) ? club
			//					.getPhoneCell() : ""); //$NON-NLS-1$
			companyEmail.setText((club.geteMail() != null) ? club.geteMail()
					: ""); //$NON-NLS-1$
			holeCount.setText(String.valueOf(club.getHolesList()));
			twainCount.setText(String.valueOf(club.getHitsToWin()));
			companyLongitude.setSelection((int) (club.getLng() * 1000000));
			companyLatitude.setSelection((int) (club.getLat() * 1000000));

		}
	}

	private Image getSwtImage(com.academysmart.bookagolf.model.Image image) {
		URL tmpURL = null;
		Image swtImage = null;
		String imageResourceId = ImageRegistryKeys.CLUB_IMAGE + image.getId();

		// urlString = urlString.replace("http:", "https:");
		if (JFaceResources.getImageRegistry().getDescriptor(imageResourceId) == null) {
			try {
				tmpURL = new URL(image.getUrl());
				JFaceResources.getImageRegistry().put(imageResourceId,
						new Image(getDisplay(), tmpURL.openStream()));
			} catch (MalformedURLException e1) {

				// e1.printStackTrace();
				Loggers.FILE_LOGGER.error("Error occured.", e1); //$NON-NLS-1$
			} catch (IOException e1) {

				// e1.printStackTrace();
				Loggers.FILE_LOGGER.error("Error occured.", e1); //$NON-NLS-1$
			}
		}
		swtImage = JFaceResources.getImage(imageResourceId);
		return swtImage;
	}

	private void loadImages() {
		final List<Image> swtImages = new ArrayList<Image>();
		MainWindow.getInstance().executorService.execute(new Runnable() {

			@Override
			public void run() {
				List<com.academysmart.bookagolf.model.Image> clubImages = ImageProvider.INSTANCE
						.getImages();
				for (com.academysmart.bookagolf.model.Image image : clubImages) {
					swtImages.add(getSwtImage(image));
				}
				Display display = MainWindow.getInstance().getShell()
						.getDisplay();
				if (!display.isDisposed()) {
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							GalleryItem photoGroup = new GalleryItem(photoList,
									SWT.None);
							photoGroup.setExpanded(true);
							photoGroup.setText(""); //$NON-NLS-1$
							for (Image image : swtImages) {
								GalleryItem photo = new GalleryItem(photoGroup,
										SWT.None);
								photo.setImage(image);
							}
							photoList.redraw();
						}
					});
				}
			}
		});
	}
}
