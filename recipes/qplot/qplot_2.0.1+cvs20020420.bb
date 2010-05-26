DESCRIPTION = "QPlot is an Advanced Matematical Calculator for Qt/Embedded based Palmtop Environments"
SECTION = "opie/applications"
PRIORITY = "optional"
LICENSE = "GPL"
HOMEPAGE = "http://qplot.sourceforge.net/"
SRCDATE = "20020420"
#Change to form x.y.z+cvs${SRCDATE} when 2.0.1 changes in PV
PV = "2.0.1-cvs-${SRCDATE}"
PR = "r1"

SRC_URI = "cvs://anonymous@qplot.cvs.sourceforge.net/cvsroot/qplot;module=qplot \
	   file://gcc3.patch"
S = "${WORKDIR}/qplot"

inherit palmtop

do_install() {
	install -d ${D}${palmtopdir}/bin \
		   ${D}${palmtopdir}/apps/Applications \
		   ${D}${palmtopdir}/pics \
		   ${D}${palmtopdir}/lib \
		   ${D}${palmtopdir}/plugins/inputmethods
        install -m 755 qplotmain/qplot ${D}${palmtopdir}/bin/qplot
        install -m 644 qplotmain/qplot.png ${D}${palmtopdir}/pics/
        install -m 644 qplotmain/qplot-const.b ${D}${palmtopdir}/lib/
        install -m 644 qplotmain/qplot-math.b ${D}${palmtopdir}/lib/
	oe_libinstall -so -C qplotmath libqplotmath ${D}${palmtopdir}/plugins/inputmethods/
        install -m 644 qplot.desktop ${D}${palmtopdir}/apps/Applications/qplot.desktop
}
