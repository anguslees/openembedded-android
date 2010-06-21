require xinput-calibrator.inc

SRC_URI = "git://github.com/tias/xinput_calibrator.git;protocol=git \
           file://0001-calibratorXorgPrint.cpp-fix-miny-and-maxx-printing-o.patch"

SRCREV = "d6e01d780001948f55006698e8e9e48c12894810"
S = "${WORKDIR}/git/"

PR = "${INC_PR}.0"

do_install_append() {
        install -d ${D}${bindir}
        install -m 0755 scripts/xinput_calibrator_pointercal.sh ${D}${bindir}/xinput_calibrator_once.sh
        ln -s ${bindir}/xinput_calibrator_x11 ${D}${bindir}/xinput_calibrator
        install -d ${D}${datadir}/applications/
        install -m 0755 scripts/xinput_calibrator.desktop ${D}${datadir}/applications/xinput-calibrator.desktop
        install -m 0755 scripts/xinput_calibrator_get_hal_calibration.sh ${D}${bindir}/xinput_calibrator_get_hal_calibration.sh
}
