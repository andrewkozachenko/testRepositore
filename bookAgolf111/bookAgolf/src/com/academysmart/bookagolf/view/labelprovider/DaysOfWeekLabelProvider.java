package com.academysmart.bookagolf.view.labelprovider;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Game;

public class DaysOfWeekLabelProvider extends ImageColorBackgroundLabelProvider {

	private static final int sevenDaysWidth = 139;
	private ColumnViewer viewer;

	public DaysOfWeekLabelProvider(ColumnViewer viewer) {
		super();
		this.viewer = viewer;
	}

	private Image makeShot(Control control, Game game) {
		// Hopefully no platform uses exactly this color because we'll make
		// it transparent in the image.
		Color greenScreen = new Color(control.getDisplay(), 222, 223, 224);

		Shell shell = new Shell(control.getShell(), SWT.NO_TRIM);

		// otherwise we have a default gray color
		shell.setBackground(greenScreen);

		Composite composite = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.spacing = 0;
		composite.setLayout(rowLayout);

		// Composite paddingComposite = new Composite(composite, SWT.FILL);

		if (game.isMon()) {
			Label monNewLabel = new Label(composite, SWT.BORDER);
			monNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			monNewLabel.setText(Messages.PricesViewListGame_Mon);
		}
		if (game.isTue()) {
			Label tueNewLabel = new Label(composite, SWT.BORDER);
			tueNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			tueNewLabel.setText(Messages.PricesViewListGame_Tue);
		}
		if (game.isWed()) {
			Label wedNewLabel = new Label(composite, SWT.BORDER);
			wedNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			wedNewLabel.setText(Messages.PricesViewListGame_Wed);
		}
		if (game.isThu()) {
			Label thuNewLabel = new Label(composite, SWT.BORDER);
			thuNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			thuNewLabel.setText(Messages.PricesViewListGame_Thu);
		}
		if (game.isFri()) {
			Label friNewLabel = new Label(composite, SWT.BORDER);
			friNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			friNewLabel.setText(Messages.PricesViewListGame_Fri);
		}
		if (game.isSat()) {
			Label satNewLabel = new Label(composite, SWT.BORDER);
			satNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			satNewLabel.setText(Messages.PricesViewListGame_Sat);
		}
		if (game.isSun()) {
			Label sunNewLabel = new Label(composite, SWT.BORDER);
			sunNewLabel.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			sunNewLabel.setText(Messages.PricesViewListGame_Sun);
		}
		composite.setBackground(greenScreen);
		// otherwise an image is located in a corner
		composite.setLocation(1, 1);
		Point bsize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		// otherwise an image is stretched by width
		bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
		// bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
		// composite.setSize(bsize);
		if ((sevenDaysWidth - bsize.x) > 0) {
			rowLayout.marginLeft = sevenDaysWidth - bsize.x;
			// paddingComposite.setBounds(0, 0, sevenDaysWidth - bsize.x,
			// bsize.y);
			bsize.x = sevenDaysWidth;
		}
		composite.setSize(bsize);
		// } else {
		// paddingComposite.dispose();
		// }

		shell.setSize(bsize);

		shell.open();

		GC gc = new GC(shell);
		Image image = new Image(control.getDisplay(), bsize.x, bsize.y);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		shell.close();

		ImageData imageData = image.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(greenScreen
				.getRGB());

		Image img = new Image(control.getDisplay(), imageData);
		image.dispose();
		greenScreen.dispose();
		return img;
	}

	private String getDaysOfWeekKey(Game game) {
		String daysOfWeekKey = ""; //$NON-NLS-1$
		if (game.isMon()) {
			daysOfWeekKey += Messages.PricesViewListGame_Mon;
		}
		if (game.isTue()) {
			daysOfWeekKey += Messages.PricesViewListGame_Tue;
		}
		if (game.isWed()) {
			daysOfWeekKey += Messages.PricesViewListGame_Wed;
		}
		if (game.isThu()) {
			daysOfWeekKey += Messages.PricesViewListGame_Thu;
		}
		if (game.isFri()) {
			daysOfWeekKey += Messages.PricesViewListGame_Fri;
		}
		if (game.isSat()) {
			daysOfWeekKey += Messages.PricesViewListGame_Sat;
		}
		if (game.isSun()) {
			daysOfWeekKey += Messages.PricesViewListGame_Sun;
		}
		if (daysOfWeekKey.isEmpty()) {
			daysOfWeekKey = "empty"; //$NON-NLS-1$
		}
		return ImageRegistryKeys.DAYS_OF_WEEK + daysOfWeekKey;
	}

	@Override
	public Image getImage(Object element) {
		Game game = (Game) element;
		String daysOfWeekKey = getDaysOfWeekKey(game);
		if (JFaceResources.getImageRegistry().get(daysOfWeekKey) == null) {
			JFaceResources.getImageRegistry().put(daysOfWeekKey,
					makeShot(viewer.getControl(), game));
		}
		return JFaceResources.getImageRegistry().get(daysOfWeekKey);

	}
}
