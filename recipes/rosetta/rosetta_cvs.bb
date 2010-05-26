SECTION = "x11/utils"
SRCDATE = "20090514"
PV = "0.0+cvs${SRCDATE}"
DEPENDS = "gtk+ libgpewidget virtual/libx11 libxrandr libxft libxtst libxext libxau \
	   virtual/libintl"
DESCRIPTION = "Multistroke / full word handwriting recognition for X"
LICENSE = "GPL"

SRC_URI = "${HANDHELDS_CVS};module=rosetta \
	file://rosetta-makefile.patch"
S = "${WORKDIR}/rosetta"
PR = "r4"

inherit pkgconfig gettext

FILES_${PN} = "${sysconfdir} ${bindir} ${datadir}/pixmaps ${datadir}/applications ${datadir}/rosetta"

do_install () {
        oe_runmake PREFIX=${prefix} DESTDIR=${D} install-program
}

pkg_postinst () {
if test "x$D" != "x"; then
	exit 1
else
  ${prefix}/bin/rosetta -P
fi
}
