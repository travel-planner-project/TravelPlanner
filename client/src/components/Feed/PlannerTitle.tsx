import { differenceInDays, getMonth } from 'date-fns'
import Icon from '../Common/Icon'
import styles from './PlannerTitle.module.scss'
import { forwardRef, Ref } from 'react'

type PlannerTitleProp = {
  plannerId: number
  planTitle: string
  startDate: string
  endDate: string
  hostName: string
  hostUrl: string
}

export default forwardRef(function PlannerTitle(
  { planTitle, startDate, endDate, hostName, hostUrl }: PlannerTitleProp,
  ref: Ref<HTMLLIElement>
) {
  const date = startDate ? getMonth(new Date(startDate)) + 1 : getMonth(new Date()) + 1
  const dateDiffer = startDate ? differenceInDays(new Date(endDate), new Date(startDate)) : 0

  return (
    <li className={styles.li} ref={ref}>
      {hostUrl ? (
        <img src={hostUrl} className={styles.img} alt='host profile' />
      ) : (
        <Icon name='profile' size={70} className={styles.profileIcon} />
      )}
      <div>
        <h3 className={styles.title}>{planTitle}</h3>
        <div className={styles.hostDateBox}>
          <div className={styles.hostName}>{hostName}님의 계획</div>
          <div className={styles.verticalLine} />
          <div className={styles.date}>
            {date}월의
            {dateDiffer > 0 ? ` ${dateDiffer}박 ${dateDiffer + 1}일 여행` : ' 당일치기 여행'}
          </div>
        </div>
      </div>
    </li>
  )
})
