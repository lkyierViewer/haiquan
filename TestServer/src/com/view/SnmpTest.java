package com.view;

import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpTest {
	private String community="gaokai";
	private Snmp snmp = null;
    private int version = 0;
    
    public void InitFactory(int version) {
        try {
            this.version = version;
            TransportMapping transprot = new DefaultUdpTransportMapping();
            snmp = new Snmp(transprot);
            if (this.version == SnmpConstants.version3) {
                //设置安全模式
                USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
                SecurityModels.getInstance().addSecurityModel(usm);
            }
            transprot.listen();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Target createTarget(String IPAddress) {
        Target target = null;
        try {
            if (version != SnmpConstants.version3 && version != SnmpConstants.version1 && version != SnmpConstants.version2c) {
                System.out.println("");
                return target;
            }
            if (version == SnmpConstants.version3) {
                target = new UserTarget();
                //snmpV3需要设置安全级别和安全名称，其中安全名称是创建snmp指定user设置的new OctetString("SNMPV3")
                target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
                target.setSecurityName(new OctetString("SNMPV3"));
            } else {
                //snmpV1和snmpV2需要指定团体名名称
                target = new CommunityTarget();
                ((CommunityTarget) target).setCommunity(new OctetString(community));
                if (version == SnmpConstants.version2c) {
                    target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
                }
            }
            target.setVersion(version);
            //目标对象相关设置
            target.setAddress(GenericAddress.parse(IPAddress));
            target.setRetries(5);
            target.setTimeout(1000);
        } catch (Exception ex) {
        } finally {
            return target;
        }
    }

    /**
     * 创建PDU
     *
     * @param type
     * @param oid
     * @return
     */
    private PDU createPDU(int type, String oid) {
        PDU pdu = null;
        try {
            if (version == SnmpConstants.version3) {
                pdu = new ScopedPDU();
            } else {
                pdu = new PDUv1();
            }
            pdu.setType(type);
            pdu.add(new VariableBinding(new OID(oid)));
        } catch (Exception ex) {
        } finally {
            return pdu;
        }
    }
    
    private String IPAddressToSnmpFormat(String svrIP) {
        Integer port = 161;
        return "udp:" + svrIP + "/" + port.toString();
    }
	
    public void SnmpWalk(String svrIP, String oid) {
        try {
            //创建目标对象
            Target target = createTarget(IPAddressToSnmpFormat(svrIP));
            //创建报文
            PDU pdu = createPDU(PDU.GETNEXT, oid);
            //发送报文并获取返回结果
            boolean matched = true;
            while (matched) {
                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();
                String nextOid = null;
                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (int i = 0; i < variableBindings.size(); i++)
                {
                    VariableBinding variableBinding=variableBindings.elementAt(i);
                    Variable variable=variableBinding.getVariable();
                    nextOid=variableBinding.getOid().toDottedString();
                    //如果不是这个节点下的oid，则终止遍历，否则会输出很多，直到整个树遍历完
                    if(!nextOid.startsWith(oid))
                    {
                        matched=false;
                        break;
                    }
                    String aaaa=variable.toString();
                    System.out.println(aaaa);
                }
                if(!matched)
                {
                    break;
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));
            }
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        } finally {

        }
    }
}
