package com.ike.commonutils.net.retrofitnetutils.threadManager;

/**
 * 线程池管理类
 */
public class ThreadManager
{
	private static ThreadPoolProxy	mLongPool;
	private static Object mLongLock		= new Object();

	private static ThreadPoolProxy	mDownloadPool;
	private static Object mDownloadLock	= new Object();
	/**
	 * 获得耗时操作的线程池
	 * 
	 * @return
	 */
	public static ThreadPoolProxy getLongPool()
	{
		if (mLongPool == null)
		{
			synchronized (mLongLock)
			{
				if (mLongPool == null)
				{
					mLongPool = new ThreadPoolProxy(2, 2, 0);
				}
			}
		}
		return mLongPool;
	}

	public static ThreadPoolProxy getDownloadPool()
	{
		if (mDownloadPool == null)
		{
			synchronized (mDownloadLock)
			{
				if (mDownloadPool == null)
				{
					mDownloadPool = new ThreadPoolProxy(1, 1, 0);
				}
			}
		}
		return mDownloadPool;
	}
}
