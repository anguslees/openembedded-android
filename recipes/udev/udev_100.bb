DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_slugos = "1"

DESCRIPTION = "udev is a daemon which dynamically creates and removes device nodes from \
/dev/, handles hotplug events and loads drivers at boot time. It replaces \
the hotplug package and requires a kernel not older than 2.6.15."
DESCRIPTION_libvolume-id = "libvolume_id shared library, \
used to detect the type of a file system and read its metadata."
DESCRIPTION_libvolume-id-dev = "libvolume_id development headers, \
needed to link programs with libvolume_id."

require udev.inc

PR = "${INC_PR}.0"

LD = "${CC}"

SRC_URI += "file://noasmlinkage.patch \
	    file://flags.patch \
	    file://mtd-exclude-persistent.patch \
	    file://mount.blacklist \
	    file://mount.sh \
	   "

FILES_${PN} += "${base_libdir}/udev/*"
FILES_${PN}-dbg += "${base_libdir}/udev/.debug"
UDEV_EXTRAS = "extras/firmware/ extras/scsi_id/ extras/volume_id/ extras/run_directory/"
EXTRA_OEMAKE += "libudevdir=/lib/udev libdir=${base_libdir} prefix="

do_install () {
	install -d ${D}${usrsbindir} \
		   ${D}${sbindir} \
		   ${D}${sysconfdir}
	oe_runmake 'DESTDIR=${D}' INSTALL=install install
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/udev

	install -d ${D}${sysconfdir}/udev/rules.d/

	install -m 0644 ${WORKDIR}/mount.blacklist     ${D}${sysconfdir}/udev/
	install -m 0644 ${WORKDIR}/local.rules         ${D}${sysconfdir}/udev/rules.d/local.rules
	install -m 0644 ${WORKDIR}/permissions.rules   ${D}${sysconfdir}/udev/rules.d/permissions.rules
	install -m 0644 ${WORKDIR}/udev.rules          ${D}${sysconfdir}/udev/rules.d/udev.rules
	install -m 0644 ${WORKDIR}/links.conf          ${D}${sysconfdir}/udev/links.conf
	if [ "${UDEV_DEVFS_RULES}" = "1" ]; then
		install -m 0644 ${WORKDIR}/devfs-udev.rules ${D}${sysconfdir}/udev/rules.d/devfs-udev.rules
	fi

	install -d ${D}${sysconfdir}/udev/scripts/

	install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
	install -m 0755 ${WORKDIR}/network.sh ${D}${sysconfdir}/udev/scripts

	install -d ${D}${base_libdir}/udev/
}

pkg_postinst_append() {

	# Add the root partition to mount.blacklist to avoid a bug in the auto-mounter,
	# causing confusion with fsck on boot

        while read dev mp fs junk
        do
                if test "$mp" = "/"
                then
                        root_partition="$dev"
                        echo "$root_partition" >> $D${sysconfdir}/udev/mount.blacklist
                fi
        done < $D${sysconfdir}/fstab

}


SRC_URI[md5sum] = "1ceb626a86630287cc28585eb16d7131"
SRC_URI[sha256sum] = "faa9b6e000509039bbd4e1f24694adacf2cd86228d22240ec1a13acc13517aa6"
