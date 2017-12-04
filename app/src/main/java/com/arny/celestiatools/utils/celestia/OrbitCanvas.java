package com.arny.celestiatools.utils.celestia;

import android.app.Activity;
import android.graphics.*;
import android.media.Image;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.celestiatools.utils.astro.*;

import static com.arny.celestiatools.utils.astro.AstroUtils.DistanceConvert;

/**
 * Orbit Canvas
 */
class OrbitCanvas extends Canvas {

	/**
	 * Orbital Element (Initialized in Constructor)
	 */
	private Comet object;

	/**
	 * Orbital Curve Class (Initialized in Constructor)
	 */
	private CometOrbit objectOrbit;
	private PlanetOrbit planetOrbit[];
	private double epochPlanetOrbit;

	/**
	 * Date
	 */
	private ATime atime;

	/**
	 * Position of the Object and Planets
	 */
	private Xyz objectPos;
	private Xyz planetPos[];
	private int CenterObjectSelected;
	private boolean OrbitDisplay[];
	/**
	 * Projection Parameters
	 */
	private double fRotateH = 0.0;
	private double fRotateV = 0.0;
	private double fZoom = 5.0;
	private double fZoomFactor = 600;

	/**
	 * Rotation Matrix
	 */
	private Matrix mtxToEcl;
	private double epochToEcl;
	private Matrix mtxRotate;
	private int nX0, nY0;    // Origin

	/**
	 * Size of Canvas
	 */
	private Dimensions sizeCanvas;

	/**
	 * Colors
	 */
	private int colorObjectOrbitUpper = Color.parseColor("#00f5ff");
	private int colorObjectOrbitLower = Color.parseColor("#0000ff");
	private int colorObject = Color.parseColor("#00ffff");
	private int colorObjectName = Color.parseColor("#00cccc");
	private int colorPlanetOrbitUpper = Color.parseColor("#ffffff");
	private int colorPlanetOrbitLower = Color.parseColor("#808080");
	private int colorPlanet = Color.parseColor("#00ff00");
	private int colorPlanetName = Color.parseColor("#00aa00");
	private int colorSun = Color.parseColor("#d04040");
	private int colorAxisPlus = Color.parseColor("#ffff00");
	private int colorAxisMinus = Color.parseColor("#555500");
	private int colorInformation = Color.parseColor("#ffffff");

	/**
	 * Fonts
	 */
//	private Font fontObjectName = new Font("Helvetica", Font.BOLD, 14);
//	private Font fontPlanetName = new Font("Helvetica", Font.PLAIN, 14);
//	private Font fontInformation = new Font("Helvetica", Font.BOLD, 14);

	/**
	 * off-screen Image
	 */
	private Image offscreen;

	/**
	 * Object Name Drawing Flag
	 */
	private boolean bPlanetName;
	private boolean bObjectName;
	private boolean bDistanceLabel;
	private boolean bDateLabel;
	private double sdistance;
	private double edistance, minEdist;
	private String strATime = "";

	public double getEdistance() {
		return edistance;
	}

	public void setMinEdist(double minEdist) {
		this.minEdist = minEdist;
	}

	/**
	 * Constructor
	 */
	public OrbitCanvas(Comet object, ATime atime) {
		planetPos = new Xyz[9];
		OrbitDisplay = new boolean[11];
		this.object = object;
		this.objectOrbit = new CometOrbit(object, 120);
		this.planetOrbit = new PlanetOrbit[9];
		updatePlanetOrbit(atime);
		updateRotationMatrix(atime);
		// Set Initial Date
		this.atime = atime;
		setDate(this.atime);
		// no offscreen image
		offscreen = null;
		// no name labels
		bPlanetName = false;
		bObjectName = false;
		bDistanceLabel = true;
		bDateLabel = true;
//		repaint();
	}

	/**
	 * Make Planet Orbit
	 */
	private void updatePlanetOrbit(ATime atime) {
		for (int i = Planet.MERCURY; i <= Planet.PLUTO; i++) {
			this.planetOrbit[i - Planet.MERCURY]
					= new PlanetOrbit(i, atime, 48);
		}
		this.epochPlanetOrbit = atime.getJd();
	}

	/**
	 * Rotation Matrix Equatorial(2000)->Ecliptic(DATE)
	 */
	private void updateRotationMatrix(ATime atime) {
		Matrix mtxPrec = Matrix.PrecMatrix(AstroConst.JD2000, atime.getJd());
		Matrix mtxEqt2Ecl = Matrix.RotateX(ATime.getEp(atime.getJd()));
		this.mtxToEcl = mtxEqt2Ecl.Mul(mtxPrec);
		this.epochToEcl = atime.getJd();
	}

	/**
	 * Horizontal Rotation Parameter Set
	 */
	public void setRotateHorz(int nRotateH) {
		this.fRotateH = (double) nRotateH;
	}

	/**
	 * Vertical Rotation Parameter Set
	 */
	public void setRotateVert(int nRotateV) {
		this.fRotateV = (double) nRotateV;
	}

	/**
	 * Zoom Parameter Set
	 */
	public void setZoom(int nZoom) {
		this.fZoom = (double) nZoom;
	}

	/**
	 * Zoom factor Parameter Set
	 */
	public void setZoomFactor(int nZoomFactor) {
		this.fZoomFactor = (double) nZoomFactor;
	}

	/**
	 * Date Parameter Set
	 */
	public void setDate(ATime atime) {
		this.atime = atime;
		objectPos = object.GetPos(atime.getJd());
		for (int i = 0; i < 9; i++) {
			planetPos[i] = Planet.getPos(Planet.MERCURY + i, atime);
		}
	}

	/**
	 * Switch Planet Name ON/OFF
	 */
	public void switchPlanetName(boolean bPlanetName) {
		this.bPlanetName = bPlanetName;
	}

	/**
	 * Select Orbits
	 */
	public void SelectOrbits(boolean OrbitDisplay[], int OrbitCount) {
		for (int i = 0; i < OrbitCount; i++) {
			this.OrbitDisplay[i] = OrbitDisplay[i];
		}
	}

	/**
	 * Select Center Object
	 */
	public void SelectCenterObject(int CenterObjectSelected) {
		this.CenterObjectSelected = CenterObjectSelected;
	}

	/**
	 * Switch Object Name ON/OFF
	 */
	public void switchObjectName(boolean bObjectName) {
		this.bObjectName = bObjectName;
	}

	/**
	 * Get (X,Y) on Canvas from Xyz
	 */
	private Point getDrawPoint(Xyz xyz) {
		// 600 means 5...fZoom...100 -> 120AU...Width...6AU
		double fMul = this.fZoom * (double) sizeCanvas.width / fZoomFactor * (1.0 + xyz.fZ / 250.0);        // Parse
		int nX = this.nX0 + (int) Math.round(xyz.fX * fMul);
		int nY = this.nY0 - (int) Math.round(xyz.fY * fMul);
		return new Point(nX, nY);
	}

	/**
	 * Draw Planets' Orbit
	 */
	private void drawPlanetOrbit(Canvas g, PlanetOrbit planetOrbit, int colorUpper, int colorLower) {
		Paint paint = new Paint();
		Point point1, point2;
		Xyz xyz = planetOrbit.getAt(0).Rotate(this.mtxToEcl)
				.Rotate(this.mtxRotate);
		point1 = getDrawPoint(xyz);
		for (int i = 1; i <= planetOrbit.getDivision(); i++) {
			xyz = planetOrbit.getAt(i).Rotate(this.mtxToEcl);
			if (xyz.fZ >= 0.0) {
				paint.setColor(colorUpper);
			} else {
				paint.setColor(colorLower);
			}
			xyz = xyz.Rotate(this.mtxRotate);
			point2 = getDrawPoint(xyz);
			g.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
			point1 = point2;
		}
	}

	/**
	 * Draw Earth's Orbit
	 */
	private void drawEarthOrbit(Canvas g, PlanetOrbit planetOrbit, int colorUpper, int colorLower) {
		Paint paint = new Paint();
		Point point1, point2;
		Xyz xyz = planetOrbit.getAt(0).Rotate(this.mtxToEcl)
				.Rotate(this.mtxRotate);
		point1 = getDrawPoint(xyz);
		for (int i = 1; i <= planetOrbit.getDivision(); i++) {
			xyz = planetOrbit.getAt(i).Rotate(this.mtxToEcl);
			paint.setColor(colorUpper);
			xyz = xyz.Rotate(this.mtxRotate);
			point2 = getDrawPoint(xyz);
			g.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
			point1 = point2;
		}
	}

	/**
	 * Draw Planets' Body
	 */
	private void drawPlanetBody(Canvas og, Xyz planetPos, String strName) {
		Paint paint = new Paint();
		Xyz xyz = planetPos.Rotate(this.mtxRotate);
		Point point = getDrawPoint(xyz);
		paint.setColor(colorPlanet);
		og.drawArc(point.x - 2, point.y - 2, 5, 5, 0, 360, true, paint);
		if (bPlanetName) {
			paint.setColor(colorPlanetName);
			og.drawText(strName, point.x + 5, point.y, paint);
		}
	}

	/**
	 * Draw Ecliptic Axis
	 */
	private void drawEclipticAxis(Canvas og) {
		Paint paint = new Paint();
		Xyz xyz;
		Point point;
		paint.setColor(colorAxisMinus);
//		og.setColor(colorAxisMinus);
		// -X
		xyz = (new Xyz(-50.0, 0.0, 0.0)).Rotate(this.mtxRotate);
		point = getDrawPoint(xyz);
		og.drawLine(this.nX0, this.nY0, point.x, point.y, paint);

		// -Z
		xyz = (new Xyz(0.0, 00.0, -50.0)).Rotate(this.mtxRotate);
		point = getDrawPoint(xyz);
		og.drawLine(this.nX0, this.nY0, point.x, point.y, paint);

		paint.setColor(colorAxisPlus);
		// +X
		xyz = (new Xyz(50.0, 0.0, 0.0)).Rotate(this.mtxRotate);
		point = getDrawPoint(xyz);
		og.drawLine(this.nX0, this.nY0, point.x, point.y, paint);
		// +Z
		xyz = (new Xyz(0.0, 00.0, 50.0)).Rotate(this.mtxRotate);
		point = getDrawPoint(xyz);
		og.drawLine(this.nX0, this.nY0, point.x, point.y, paint);
	}

	/**
	 * update (paint without clearing background)
	 */
	public void update(Canvas g, Activity activity) {
		Point point3;
		Xyz xyz, xyz1;

		// Calculate Drawing Parameter
		Matrix mtxRotH = Matrix.RotateZ(this.fRotateH * Math.PI / 180.0);
		Matrix mtxRotV = Matrix.RotateX(this.fRotateV * Math.PI / 180.0);
		this.mtxRotate = mtxRotV.Mul(mtxRotH);

		this.nX0 = this.sizeCanvas.width / 2;
		this.nY0 = this.sizeCanvas.height / 2;

		if (Math.abs(epochToEcl - atime.getJd()) > 365.2422 * 5) {
			updateRotationMatrix(atime);
		}

		// If center object is comet/asteroid
		if (CenterObjectSelected == 1) {
//			xyz = this.objectOrbit.getAt(0).Rotate(this.mtxToEcl).Rotate(this.mtxRotate);
			xyz = this.objectPos.Rotate(this.mtxToEcl).Rotate(this.mtxRotate);
			point3 = getDrawPoint(xyz);

			this.nX0 = this.sizeCanvas.width - point3.x;
			this.nY0 = this.sizeCanvas.height - point3.y;

			if (Math.abs(epochToEcl - atime.getJd()) > 365.2422 * 5) {
				updateRotationMatrix(atime);
			}
		}
		// If center object is one of the planets
		else if (CenterObjectSelected > 1) {
			xyz = planetPos[CenterObjectSelected - 2].Rotate(this.mtxRotate);

			point3 = getDrawPoint(xyz);

			this.nX0 = this.sizeCanvas.width - point3.x;
			this.nY0 = this.sizeCanvas.height - point3.y;

			if (Math.abs(epochToEcl - atime.getJd()) > 365.2422 * 5) {
				updateRotationMatrix(atime);
			}
		}
		Bitmap bitmap = Bitmap.createBitmap((int) activity.getWindowManager()
				.getDefaultDisplay().getWidth(), (int) activity.getWindowManager()
				.getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
		Canvas og = new Canvas(bitmap);
		// Get Off-Screen Image Graphics Context
//		Graphics og = offscreen.getGraphics();

		// Draw Frame
//		og.setColor(Color.BLACK);
//		og.fillRect(0, 0, sizeCanvas.width - 1, sizeCanvas.height - 1);
//
//		// Draw Ecliptic Axis
//		drawEclipticAxis(og);
//
//		// Draw Sun
//		og.setColor(colorSun);
//		og.drawArc(this.nX0 - 2, this.nY0 - 2, 5, 5, 0, 360);

		// Draw Orbit of Object

		xyz = this.objectOrbit.getAt(0).Rotate(this.mtxToEcl)
				.Rotate(this.mtxRotate);
		Point point1, point2;
		point1 = getDrawPoint(xyz);
		if (OrbitDisplay[0] || OrbitDisplay[1]) {

			for (int i = 1; i <= this.objectOrbit.getDivision(); i++) {
				xyz = this.objectOrbit.getAt(i).Rotate(this.mtxToEcl);
				if (xyz.fZ >= 0.0) {
//					og.setColor(colorObjectOrbitUpper);
				} else {
//					og.setColor(colorObjectOrbitLower);
				}
				xyz = xyz.Rotate(this.mtxRotate);
				point2 = getDrawPoint(xyz);
//				og.drawLine(point1.x, point1.y, point2.x, point2.y);
				point1 = point2;
			}
		}

		// Draw Object Body
		xyz = this.objectPos.Rotate(this.mtxToEcl).Rotate(this.mtxRotate);
		point1 = getDrawPoint(xyz);
//		og.setColor(colorObject);
//		og.fillArc(point1.x - 2, point1.y - 2, 5, 5, 0, 360);
//		og.setFont(fontObjectName);
		if (bObjectName) {
//			og.setColor(colorObjectName);
//			og.drawString(object.getName(), point1.x + 5, point1.y);
		}

		//  Draw Orbit of Planets
		if (Math.abs(epochPlanetOrbit - atime.getJd()) > 365.2422 * 5) {
			updatePlanetOrbit(atime);
		}
//		og.setFont(fontPlanetName);

		if (OrbitDisplay[0] || OrbitDisplay[10]) {
			drawPlanetOrbit(og, planetOrbit[Planet.PLUTO - Planet.MERCURY],
					colorPlanetOrbitUpper, colorPlanetOrbitLower);
		}
		drawPlanetBody(og, planetPos[8], "Pluto");

		if (OrbitDisplay[0] || OrbitDisplay[9]) {

			drawPlanetOrbit(og, planetOrbit[Planet.NEPTUNE - Planet.MERCURY],
					colorPlanetOrbitUpper, colorPlanetOrbitLower);
		}
		drawPlanetBody(og, planetPos[7], "Neptune");

		if (OrbitDisplay[0] || OrbitDisplay[8]) {
			drawPlanetOrbit(og, planetOrbit[Planet.URANUS - Planet.MERCURY],
					colorPlanetOrbitUpper, colorPlanetOrbitLower);
		}
		drawPlanetBody(og, planetPos[6], "Uranus");

		if (OrbitDisplay[0] || OrbitDisplay[7]) {
			drawPlanetOrbit(og, planetOrbit[Planet.SATURN - Planet.MERCURY],
					colorPlanetOrbitUpper, colorPlanetOrbitLower);
		}
		drawPlanetBody(og, planetPos[5], "Saturn");

		if (OrbitDisplay[0] || OrbitDisplay[6]) {
			drawPlanetOrbit(og, planetOrbit[Planet.JUPITER - Planet.MERCURY],
					colorPlanetOrbitUpper, colorPlanetOrbitLower);
		}
		drawPlanetBody(og, planetPos[4], "Jupiter");

		if (fZoom * 1.524 >= 7.5) {
			if (OrbitDisplay[0] || OrbitDisplay[5]) {

				drawPlanetOrbit(og, planetOrbit[Planet.MARS - Planet.MERCURY],
						colorPlanetOrbitUpper, colorPlanetOrbitLower);
			}
			drawPlanetBody(og, planetPos[3], "Mars");
		}
		if (fZoom * 1.000 >= 7.5) {
			if (OrbitDisplay[0] || OrbitDisplay[4]) {

				drawEarthOrbit(og, planetOrbit[Planet.EARTH - Planet.MERCURY],
						colorPlanetOrbitUpper, colorPlanetOrbitUpper);
			}
			drawPlanetBody(og, planetPos[2], "Earth");

		}
		if (fZoom * 0.723 >= 7.5) {
			if (OrbitDisplay[0] || OrbitDisplay[3]) {
				drawPlanetOrbit(og, planetOrbit[Planet.VENUS - Planet.MERCURY],
						colorPlanetOrbitUpper, colorPlanetOrbitLower);
			}
			drawPlanetBody(og, planetPos[1], "Venus");
		}
		if (fZoom * 0.387 >= 7.5) {
			if (OrbitDisplay[0] || OrbitDisplay[2]) {
				drawPlanetOrbit(og, planetOrbit[0],
						colorPlanetOrbitUpper, colorPlanetOrbitLower);
			}
			drawPlanetBody(og, planetPos[0], "Mercury");
		}

		// Information
//		og.setFont(fontInformation);
//		og.setColor(colorInformation);
//		FontMetrics fm = og.getFontMetrics();

		// Object Name String
//		point1.x = fm.charWidth('A');
//		point1.y = 2 * fm.charWidth('A');
//		og.drawString(object.getName(), point1.x, point1.y);

//		if (bDistanceLabel) {
		// Earth & Sun Distance
		double xdiff, ydiff, zdiff;
		String strDist;
		xyz = this.objectPos.Rotate(this.mtxToEcl).Rotate(this.mtxRotate);
		xyz1 = planetPos[2].Rotate(this.mtxRotate);
		sdistance = Math.sqrt((xyz.fX * xyz.fX) + (xyz.fY * xyz.fY) + (xyz.fZ * xyz.fZ));
		sdistance = (int) (sdistance * 1000.0) / 1000.0;
		xdiff = xyz.fX - xyz1.fX;
		ydiff = xyz.fY - xyz1.fY;
		zdiff = xyz.fZ - xyz1.fZ;
		edistance = Math.sqrt((xdiff * xdiff) + (ydiff * ydiff) + (zdiff * zdiff)) - DistanceConvert(AstroConst.R_Earth, AstroUtils.DistanceTypes.metre, AstroUtils.DistanceTypes.AU);
		strDist = "Earth Distance: " + MathUtils.round(DistanceConvert(edistance, AstroUtils.DistanceTypes.AU, AstroUtils.DistanceTypes.km), 3) + " km, min:" + MathUtils.round(DistanceConvert(minEdist, AstroUtils.DistanceTypes.AU, AstroUtils.DistanceTypes.km), 3) + " date:" + strATime;
//		point1.x = fm.charWidth('A');
//		point1.y = this.sizeCanvas.height - fm.getDescent() - fm.getHeight() - 10;
//		og.drawString(strDist, point1.x, point1.y);
		strDist = "Sun Distance  : " + sdistance + " AU";
//		point1.x = fm.charWidth('A');
//		point1.y = this.sizeCanvas.height - fm.getDescent() - fm.getHeight() / 3;
//		og.drawString(strDist, point1.x, point1.y);
//		}

		if (bDateLabel) {
			// Date String

//			String strDate = ATime.getMonthAbbr(atime.getMonth()) + " " + atime.getDay() + ", " + atime.getYear();
			String strDate = DateTimeUtils.getDateTime(AstroUtils.DateFromJD(atime.getJd()), "HH:mm dd MMM yyyy");
//			point1.x = this.sizeCanvas.width - fm.stringWidth(strDate) - fm.charWidth('A');
//			point1.y = this.sizeCanvas.height - fm.getDescent() - fm.getHeight() / 3;
//			point1.y = 2 * fm.charWidth('A');
//			og.drawString(strDate, point1.x, point1.y);
		}

		// Border
//		og.clearRect(0, sizeCanvas.height - 1, sizeCanvas.width, sizeCanvas.height);
//		og.clearRect(sizeCanvas.width - 1, 0, sizeCanvas.width, sizeCanvas.height);
//		g.drawImage(offscreen, 0, 0, null);
	}

	/**
	 * paint if invalidate the canvas
	 */
//	public void paint(Graphics g) {
//		if (offscreen == null) {
//			this.sizeCanvas = size();
//			offscreen = createImage(this.sizeCanvas.width, this.sizeCanvas.height);
//			update(g);
//		} else {
//			g.drawImage(offscreen, 0, 0, null);
//		}

	public void setStrATime(String strATime) {
		this.strATime = strATime;
	}
}
