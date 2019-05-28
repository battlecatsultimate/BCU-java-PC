package util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class AnimU
{
	protected MaAnim[] anims;
	protected ImgCut imgcut;
	protected MaModel mamodel;
	private BufferedImage catImage;
	private BufferedImage[] parts;

	protected boolean partial = false;

	public AnimU(String fileNamePrefix)
	{
		try
		{
			// 動畫圖片: dio.png
			catImage = ImageIO.read(new File(fileNamePrefix + ".png"));
			
			// 零件座標: dio.imgcut
			imgcut = new ImgCut(fileNamePrefix + ".imgcut");
			
			// 截取零件圖片
			parts = imgcut.cut(catImage);
			
			// dio.mamodel (上下共2組設定)
			mamodel = new MaModel(fileNamePrefix + ".mamodel");
			
			
			File zombieFile = new File(fileNamePrefix + "_zombie00.maanim");
			File entryFile = new File(fileNamePrefix + "_entry.maanim");
			
			if (zombieFile.exists())
				anims = new MaAnim[7];
			else if (entryFile.exists())
				anims = new MaAnim[5];
			else
				anims = new MaAnim[4];
			
			for (int i = 0; i < 4; i++)
			{
				// dio_00.maanim | dio_01.maanim | dio_02.maanim | dio_03.maanim 
				anims[i] = new MaAnim(fileNamePrefix + "0" + i + ".maanim");
			}
			
			if (anims.length == 5)
			{
				// dio_entry.maanim
				anims[4] = new MaAnim(fileNamePrefix + "_entry.maanim");
			}
			
			if (anims.length == 7)
			{
				for (int i = 0; i < 3; i++)
				{
					// dio_zombie00.maanim | dio_zombie01.maanim | dio_zombie02.maanim 
					anims[i + 4] = new MaAnim(fileNamePrefix + "_zombie0" + i + ".maanim");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public EAnimU getEAnim(int t)
	{
		if (mamodel == null || t >= anims.length || anims[t] == null)
			return null;
		return new EAnimU(this, t);
	}

	public BufferedImage parts(int i)
	{
		if (i < 0 || i >= parts.length)
			return null;
		return parts[i];
	}
}
