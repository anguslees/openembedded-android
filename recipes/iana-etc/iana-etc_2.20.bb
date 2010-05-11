DESCRIPTION = "The iana-etc package provides the Unix/Linux /etc/services and /etc/protocols files."
AUTHOR = "Seth W. Klein"
HOMEPAGE = "http://www.sethwklein.net/projects/iana-etc/"
SECTION = "base"
LICENSE = "OPL"
PR = "r1"

SRC_URI = "http://www.sethwklein.net/projects/iana-etc/downloads/${P}.tar.bz2"

# Don't install as /etc/protocols and /etc/services since they are installed
# by net-base and are considered config files. Install this side by side so
# end-user can manaulyl copy them and/or take entries from them. See #2505.

do_install() {
        install -d ${D}${sysconfdir}
        install -m 644 protocols ${D}${sysconfdir}/protocols.iana
        install -m 644 services ${D}${sysconfdir}/services.iana
}

SRC_URI[md5sum] = "51d584b7b6115528c21e8ea32250f2b1"
SRC_URI[sha256sum] = "754038a9eb8f1d0d6559d5b124a68e1e530a76366cff251d79536ec89e2fda21"
