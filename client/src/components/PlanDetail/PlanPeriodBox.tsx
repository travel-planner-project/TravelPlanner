import styles from './PlanPeriodBox.module.scss'
import Icon from '../Common/Icon'
import { dateFormatDash } from '../../utils/date'
import { DateListType } from '../../types/planDetailTypes'

type PlanPeriodBoxProps = {
  dateListData: DateListType
}

function PlanPeriodBox({ dateListData }: PlanPeriodBoxProps) {
  return (
    <div className={styles.planPeriodBox}>
      <div className={styles.planPeriod}>
        <Icon name='calendar' size={16} />
        <div className={styles.dateBox}>
          <div className={styles.startDate}>
            {dateListData?.length >= 1
              ? dateFormatDash(new Date(dateListData[0].dateTitle))
              : dateFormatDash(new Date())}
          </div>
          <span> ~ </span>
          <div className={styles.endDate}>
            {dateListData?.length >= 2
              ? dateFormatDash(new Date(dateListData[dateListData.length - 1].dateTitle))
              : dateFormatDash(new Date())}
          </div>
        </div>
      </div>
    </div>
  )
}

export default PlanPeriodBox
