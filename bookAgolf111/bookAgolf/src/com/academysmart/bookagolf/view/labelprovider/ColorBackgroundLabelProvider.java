package com.academysmart.bookagolf.view.labelprovider;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.TableItem;

import com.academysmart.bookagolf.config.ColorRegistryKeys;

public class ColorBackgroundLabelProvider extends ColumnLabelProvider {
	boolean even = true;
	Color oddRowColor;
	Color evenRowColor;
	OptimizedIndexSearcher searcher = new OptimizedIndexSearcher();

	public ColorBackgroundLabelProvider() {
		oddRowColor = JFaceResources.getColorRegistry().get(
				ColorRegistryKeys.ODD_ROW);

		evenRowColor = JFaceResources.getColorRegistry().get(
				ColorRegistryKeys.EVEN_ROW);
	}

	@Override
	public Color getBackground(Object element) {
		if (even) {
			return evenRowColor;
		} else {
			return oddRowColor;
		}
	}

	@Override
	public void update(ViewerCell cell) {
		even = searcher.isEven((TableItem) cell.getItem());
		super.update(cell);
	}

}
