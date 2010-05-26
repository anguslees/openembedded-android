# Angus Mackay's ez-ipupdate from www.ez-ipupdate.com
DESCRIPTION = "A client for automatically updating your EZ-IP.net, justlinux.com, dhs.org, dyndns.org, ods.org, gnudip.cheapnet.net, tzo.com, easydns.com dynamic hostname parameters. Includes daemon support that only sends updates if your IP address changes."
HOMEPAGE = "http://www.ez-ipupdate.com/"
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "GPL"
PR = "r2"

SRC_URI = "http://www.ez-ipupdate.com/dist/ez-ipupdate-${PV}.tar.gz \
	   file://configure.patch \
	   file://conffile.patch \
	   file://zoneedit.patch \
	   file://CVE-2004-0980.patch;striplevel=0 \
	   file://init \
	   file://ipupdate.conf \
	  "

INITSCRIPT_NAME = "ipupdate"
# No dependencies, so just go in at the standard level (20)
INITSCRIPT_PARAMS = "defaults"

# The configuration file must be editted...
CONFFILES_${PN} = "${sysconfdir}/ipupdate.conf"

inherit autotools update-rc.d

do_install_append() {
	install -d "${D}${sysconfdir}/init.d"
	install -c -m 755 ${WORKDIR}/init "${D}${sysconfdir}/init.d/ipupdate"
	install -c -m 644 ${WORKDIR}/ipupdate.conf "${D}${sysconfdir}/ipupdate.conf"
}

SRC_URI[md5sum] = "6505c9d18ef6b5ce13fe2a668eb5724b"
SRC_URI[sha256sum] = "f7ff9bf972139b303616018a6937aa4c6df4e93c935ffd004b30845e2ad41ea6"
