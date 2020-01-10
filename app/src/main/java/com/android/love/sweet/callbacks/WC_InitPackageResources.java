package com.android.love.sweet.callbacks;

import android.content.res.XResources;

import com.android.love.sweet.ISweetCookInitPackageResources;
import com.android.love.sweet.SweetBridge.CopyOnWriteSortedSet;

/**
 * This class is only used for internal purposes, except for the {@link InitPackageResourcesParam}
 * subclass.
 */
public abstract class WC_InitPackageResources extends WCallback implements ISweetCookInitPackageResources {
	/**
	 * Creates a new callback with default priority.
	 * @hide
	 */
	@SuppressWarnings("deprecation")
	public WC_InitPackageResources() {
		super();
	}

	/**
	 * Creates a new callback with a specific priority.
	 *
	 * @param priority See {@link WCallback#priority}.
	 * @hide
	 */
	public WC_InitPackageResources(int priority) {
		super(priority);
	}

	/**
	 * Wraps information about the resources being initialized.
	 */
	public static final class InitPackageResourcesParam extends WCallback.Param {
		/** @hide */
		public InitPackageResourcesParam(CopyOnWriteSortedSet<WC_InitPackageResources> callbacks) {
			super(callbacks);
		}

		/** The name of the package for which resources are being loaded. */
		public String packageName;

		/**
		 * Reference to the resources that can be used for calls to
		 * {@link XResources#setReplacement(String, String, String, Object)}.
		 */
		public XResources res;
	}

	/** @hide */
	@Override
	protected void call(Param param) throws Throwable {
		if (param instanceof InitPackageResourcesParam)
			handleInitPackageResources((InitPackageResourcesParam) param);
	}
}
