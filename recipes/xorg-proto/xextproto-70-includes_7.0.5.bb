require xextproto_7.0.5.bb
PR = "${INC_PR}.0"

EXTRA_OECONF += "--includedir=${includedir}/xextproto-70"

do_install_append() {
        rm -r ${D}${libdir}
}
# xorg-proto-common.inc would stage all. We need to overwrite it.
do_stage() {
        autotools_stage_includes
}

# Build of xserver-kdrive is not possible with xextproto >= 7.1.
# This package allows to install old 7.0 includes in parallel.
BPN = "xextproto"

# No, we really do not want to install .pc file and overwrite newer one:
pkgconfig_sysroot_preprocess() {
}
