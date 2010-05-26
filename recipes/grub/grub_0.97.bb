DESCRIPTION = "GRand Unified Bootloader"
HOMEPAGE = "http://www.gnu.org/software/grub"
SECTION = "bootloaders"
PRIORITY = "optional"
RDEPENDS_${PN}-install = "diffutils"
PR = "r6"

SRC_URI = "ftp://alpha.gnu.org/gnu/grub/grub-${PV}.tar.gz \
           file://automake-1.10.patch \
           file://grub-0.97-disk_geometry-1.patch \
           file://grub-support-256byte-inode.diff \
           file://menu.lst"

inherit autotools

do_install_append() {
        install -m 0644 -D ${WORKDIR}/menu.lst ${D}/boot/grub/menu.lst

        # Copy stage1/1_5/2 files to /boot/grub
        GRUB_TARGET_ARCH=$(echo ${TARGET_ARCH} | sed -e 's/.86/386/')
        install -m 0644 \
                ${D}/${libdir}/grub/${GRUB_TARGET_ARCH}${TARGET_VENDOR}/* \
                ${D}/boot/grub/
}

PACKAGES =+ "${PN}-install ${PN}-eltorito"

FILES_${PN}-install = " \
        ${sbindir}/grub-install \
        ${sbindir}/grub-terminfo \
        ${sbindir}/grub-md5-crypt \
        ${bindir}/mbchk \
        ${libdir}/grub \
"
FILES_${PN}-eltorito = "/boot/grub/stage2_eltorito"
FILES_${PN} += "/boot"

COMPATIBLE_HOST = "i.86.*-linux"

SRC_URI[md5sum] = "cd3f3eb54446be6003156158d51f4884"
SRC_URI[sha256sum] = "4e1d15d12dbd3e9208111d6b806ad5a9857ca8850c47877d36575b904559260b"
