package pl.mn.communicator.gui.util;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
public class ResourceManager {
	private static HashMap m_ClassImageMap = new HashMap();
	public static Image getImage(Class clazz, String path) {
		String key = clazz.getName() + "|" + path;
		Image image = (Image) m_ClassImageMap.get(key);
		if (image == null) {
			image =
				new Image(
					Display.getCurrent(),
					clazz.getResourceAsStream(path));
			m_ClassImageMap.put(key, image);
		}
		return image;
	}
	public static ImageDescriptor getImageDescriptor(
		Class clazz,
		String path) {
		return ImageDescriptor.createFromFile(clazz, path);
	}
}
