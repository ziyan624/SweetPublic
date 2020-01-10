package android.content.res;

import android.app.AndroidAppHelper;
import android.util.DisplayMetrics;

import com.android.love.sweet.ISweetCookInitPackageResources;
import com.android.love.sweet.ISweetCookZygoteInit;
import com.android.love.sweet.ISweetCookZygoteInit.StartupParam;
import com.android.love.sweet.callbacks.WC_InitPackageResources.InitPackageResourcesParam;

/**
 * Provides access to resources from a certain path (usually the module's own path).
 */
public class XModuleResources extends Resources {
	private XModuleResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
	}

	/**
	 * Creates a new instance.
	 *
	 * <p>This is usually called with {@link StartupParam#modulePath} from
	 * {@link ISweetCookZygoteInit#initZygote} and {@link InitPackageResourcesParam#res} from
	 * {@link ISweetCookInitPackageResources#handleInitPackageResources} (or {@code null} for
	 * system-wide replacements).
	 *
	 * @param path The path to the APK from which the resources should be loaded.
	 * @param origRes The resources object from which settings like the display metrics and the
	 *                configuration should be copied. May be {@code null}.
	 */
	public static XModuleResources createInstance(String path, XResources origRes) {
		if (path == null)
			throw new IllegalArgumentException("path must not be null");

		AssetManager assets = new AssetManager();
		assets.addAssetPath(path);

		XModuleResources res;
		if (origRes != null)
			res = new XModuleResources(assets, origRes.getDisplayMetrics(),	origRes.getConfiguration());
		else
			res = new XModuleResources(assets, null, null);

		AndroidAppHelper.addActiveResource(path, res);
		return res;
	}

	/**
	 * Creates an {@link XResForwarder} instance that forwards requests to {@code id} in this resource.
	 */
	public XResForwarder fwd(int id) {
		return new XResForwarder(this, id);
	}
}
