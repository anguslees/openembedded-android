#
# Bootloader for sh4 based devices using a HDD or block translation
# layer over the flash. NOTE that this version has the 1024 cylinder
# limit, so you probably need a seperate /boot partition as the first
# partition on the disk. Tested on the NP51R (Titan) booting from the
# onboard flash
#
DESCRIPTION = "LILO (LInux LOader) is a basic system program which \
boots your Linux system. LILO loads the Linux kernel from a floppy or \
a hard drive, boots the kernel, and passes control of the system to \
the kernel. LILO can also boot other operating systems. LILO-sh is a \
port of LILO to the SH processor."
SECTION = "bootloaders"
LICENSE = "MIT"
PR = "r1"

SRC_URI = "http://twibble.org/dist/sh4/src/lilosh/lilo-21.tar.gz \
           file://lilo-0.21-include.patch \
           file://lilo-0.21-1.1.patch \
           file://lilo.patch \
           file://lilo.raid1;apply=yes \
           file://lilo-0.21-loopdev.patch \
           file://lilo-0.21-second.patch;striplevel=0 \
           file://lilo-sh-linux.patch \
           file://lilo-linkgear.patch \
           file://lilo-noconfig-h.patch \
	   file://lilo-replace-PAGE_SIZE.patch \
	"

S = "${WORKDIR}/lilo"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

do_install() {
        # Create the base directores
        mkdir -p ${D}${sysconfdir} ${D}${sbindir}
        oe_runmake ROOT=${D} install
        # We don't ship this file
        rm ${D}/usr/sbin/keytab-lilo.pl
}

# Include /boot in the package
FILES_${PN} = "${base_sbindir} /boot"

# Works for sh3/sh4 only
COMPATIBLE_HOST = "sh.*-linux"

SRC_URI[md5sum] = "1e975d827ad96e1942df73f63512178e"
SRC_URI[sha256sum] = "da2340aca2eaf451243d2e4c2bf66aa4b80cc0abaef96860da8daf5db1cae12f"
