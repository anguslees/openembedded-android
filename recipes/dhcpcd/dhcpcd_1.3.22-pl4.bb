SECTION = "console/network"
LICENSE = "GPL"
DESCRIPTION = "dhcpcd is an RFC2131-, RFC2132-, and \
RFC1541-compliant DHCP client daemon. It gets an IP address \
and other information from the DHCP server, automatically \
configures the network interface, and tries to renew the \
lease time according to RFC2131 or RFC1541."
PR = "r1"

sbindir = "/sbin"

SRC_URI = "http://www.phystech.com/ftp/dhcpcd-${PV}.tar.gz \
	   file://config_dir.patch \
	   file://paths.patch"

inherit autotools


SRC_URI[md5sum] = "dd627a121e43835bead3ffef5b1a72fd"
SRC_URI[sha256sum] = "f435e14e1f54dc8792f4e655463d07f2bdbe6d8a8581bd62f5167334ab18bb87"
