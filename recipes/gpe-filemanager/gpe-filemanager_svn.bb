DESCRIPTION = "GPE file manager"
SECTION = "gpe"
LICENSE = "GPL"
DEPENDS = "libgpewidget gnome-vfs dbus-glib"
RDEPENDS_${PN} = "gpe-icons"
RRECOMMENDS_${PN} = "gnome-vfs-plugin-file gnome-vfs-plugin-smb gnome-vfs-plugin-ftp gnome-vfs-plugin-computer gnome-vfs-plugin-network gnome-vfs-plugin-sftp gnome-vfs-plugin-http"
PR = "r2"
PV = "0.25+svn-${SRCDATE}"

inherit autotools

SRC_URI = "${GPE_SVN} \
	   file://svn-build.patch"

S = "${WORKDIR}/${PN}"

FILES_${PN} += " ${datadir}/gpe"
DEFAULT_PREFERENCE = "-1"
