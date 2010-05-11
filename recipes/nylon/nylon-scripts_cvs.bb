DESCRIPTION = "This package provides the nylon specific init and configuration scripts."
HOMEPAGE = "http://meshcube.org/meshwiki/NyLon"
DEPENDS = "hostap-utils"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPLv2"
#SRCDATE = "20060923"
SRCDATE = "20051022"
PV = "1.0.0+cvs${SRCDATE}"
PR = "r2"
RDEPENDS = "bash"

SRC_URI = "http://download.berlin.freifunk.net/meshcube.org/nylon/stable/sources/${PN}_gruen.4g__${SRCDATE}.tar.gz \
	file://firewall-bash.diff;patch=1"
S = "${WORKDIR}/${PN}"
PACKAGE_STRIP = "no"

do_install() {
	install -d -m 755 ${D}
	(cd ${S}; tar -c --exclude .svn -f - . ) | tar -C ${D} -xpf -
}

pkg_postinst() {
if test "x$D" != "x"; then
	exit 1
else
	update-rc.d -s hostap defaults 14
	update-rc.d -s firewall defaults 16
	update-rc.d -s routing defaults 17
	update-rc.d -s emergency-ip defaults 98
	update-rc.d -s flash-backup start 38 S . stop 38 0 6 .
	update-rc.d -s dummydate start 50 S . stop 50 0 6 .

	if ! grep -q flash-backup /etc/cron/crontabs/root; then
		echo "adding flash-backup crontab"
		test -d /etc/cron/crontabs || mkdir -p /etc/cron/crontabs
		echo "0 * * * *    /etc/init.d/flash-backup backup" >> /etc/cron/crontabs/root
	fi

	if ! grep -q reset-wlan /etc/cron/crontabs/root; then
		echo "adding reset-wlan crontab"
		test -d /etc/cron/crontabs || mkdir -p /etc/cron/crontabs
		echo "0 3 * * *    /usr/sbin/reset-wlan" >> /etc/cron/crontabs/root
	fi

	update-rc.d -s busybox-cron defaults
fi
}

pkg_postrm() {
#!/bin/sh -e
update-rc.d hostap remove
update-rc.d firewall remove
update-rc.d routing remove
update-rc.d emergency-ip remove
update-rc.d flash-backup remove
update-rc.d dummydate remove
}

CONFFILES_${PN} = "/etc/nylon/backup.list /etc/nylon/hostap.conf /etc/nylon/check-process.list \
	/etc/nylon/interfaces.conf /etc/nylon/route.list /etc/nylon/wds-bridge.conf"

SRC_URI[md5sum] = "20b1b08f4fea0d18b5ac3179656b6e5d"
SRC_URI[sha256sum] = "4439ff69bfd0051a51304390619f0392ccb59f606b383d01fbebd554c8eeaf57"
