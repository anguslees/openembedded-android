DESCRIPTION = "GUI for CLI program spoje.py (http://code.google.com/p/spoje/) for searching public transport connections in Czech Republic."
HOMEPAGE = "http://multi.xeres.cz/programovani/spojegui"
LICENSE = "GPLv3"
AUTHOR = "Josef Jebavy <josef.jebavy@gmail.com>"
SECTION = "x11/applications"
PRIORITY = "optional"
RDEPENDS_${PN} = "python-core python-elementary python-sqlite3 python-netclient"

SRCREV = "64"
PV = "0.4.2+svnr${SRCPV}"
PR = "r1"

S = "${WORKDIR}/build"

PACKAGE_ARCH = "all"

SRC_URI = "svn://xeres.cz/spoje;module=build"

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${S}/src/spoje.py" "${D}/${bindir}"
  install -m 0755 "${S}/src/spoje-gui.py" "${D}/${bindir}"
  install -d "${D}/${datadir}/pixmaps"
  install -m 0644 "${S}/img/train.png" "${D}/${datadir}/pixmaps"
  install -d "${D}/${datadir}/applications"
  install -m 0644 "${S}/data/spoje.desktop" "${D}/${datadir}/applications"
}

FILES_${PN} += " ${datadir}/applications/spoje.desktop ${datadir}/pixmaps"
