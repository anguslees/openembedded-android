PR = "r0"

require ../binutils/binutils.inc

RCONFLICTS = "binutils"
RREPLACES = "binutils"
RCONFLICTS_binutils-noemu-symlinks = "binutils-symlinks"
RREPLACES_binutils-noemu-symlinks = "binutils-symlinks"

SRC_URI = "\
     ${GNU_MIRROR}/binutils/binutils-${PV}.tar.bz2 \
     file://binutils-2.16.91.0.6-objcopy-rename-errorcode.patch \
     file://binutils-configure-texinfo-version.patch \
     file://binutils-uclibc-100-uclibc-conf.patch \
     file://110-arm-eabi-conf.patch \
     file://binutils-uclibc-300-001_ld_makefile_patch.patch \
     file://binutils-uclibc-300-006_better_file_error.patch \
     file://binutils-uclibc-300-012_check_ldrunpath_length.patch \
     "

# powerpc patches
SRC_URI += "file://binutils-2.16.1-e300c2c3.patch"

EXTRA_OECONF = "--program-prefix=${TARGET_PREFIX} --disable-shared"


do_configure () {
    CC=gcc AS=as LD=ld CXX=g++ AR=ar OBJCOPY=objcopy OBJDUMP=objdump RANLIB=ranlib NM=nm STRIP=strip oe_runconf
}

do_compile() {
    make configure-host LDFLAGS=\"\" 
    make LDFLAGS=\"-all-static\"
}

do_stage() {
    :
}

SRC_URI[md5sum] = "9d22ee4dafa3a194457caf4706f9cf01"
SRC_URI[sha256sum] = "487a33a452f0edcf1f8bb8fc23dff5c7a82edec3f3f8b65632b6c945e961ee9b"
