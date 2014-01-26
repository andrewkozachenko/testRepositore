package com.academysmart.bookagolf.view.labelprovider;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.academysmart.bookagolf.config.ColorRegistryKeys;

abstract public class ImageColorBackgroundLabelProvider extends
		OwnerDrawLabelProvider {

	OptimizedIndexSearcher searcher = new OptimizedIndexSearcher();
	Color oddRowColor;
	Color evenRowColor;
	boolean even = true;

	public ImageColorBackgroundLabelProvider() {
		oddRowColor = JFaceResources.getColorRegistry().get(
				ColorRegistryKeys.ODD_ROW);
		evenRowColor = JFaceResources.getColorRegistry().get(
				ColorRegistryKeys.EVEN_ROW);
	}

	abstract public Image getImage(Object element);

	@Override
	protected void measure(Event event, Object element) {
		event.height = getImage(element).getBounds().height;

	}

	@Override
	protected void paint(Event event, Object element) {
		Image img = getImage(element);

		if (img != null) {
			Rectangle bounds;

			if (event.item instanceof TableItem) {
				bounds = ((TableItem) event.item).getBounds(event.index);
			} else {
				bounds = ((TreeItem) event.item).getBounds(event.index);
			}
			even = searcher.isEven((TableItem) event.item);
			if (even) {
				event.gc.setBackground(evenRowColor);
			} else {
				event.gc.setBackground(oddRowColor);
			}
			event.gc.fillRectangle(bounds.x, bounds.y, bounds.width,
					bounds.height);

			Rectangle imgBounds = img.getBounds();
			bounds.width /= 2;
			bounds.width -= imgBounds.width / 2;
			bounds.height /= 2;
			bounds.height -= imgBounds.height / 2;

			int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
			int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

			if (SWT.getPlatform().equals("carbon")) { //$NON-NLS-1$
				event.gc.drawImage(img, x + 2, y - 1);
			} else {
				event.gc.drawImage(img, x, y - 1);
			}

		}
	}

}
